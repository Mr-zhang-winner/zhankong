package com.ruoyi.xkd.protocol.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Service;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.xkd.domain.TAlarmLog;
import com.ruoyi.xkd.domain.TAntennaProtocolLog;
import com.ruoyi.xkd.domain.TDeviceStatus;
import com.ruoyi.xkd.domain.dto.DeviceStatusPushDTO;
import com.ruoyi.xkd.domain.dto.WebSocketMessage;
import com.ruoyi.xkd.mapper.TAlarmLogMapper;
import com.ruoyi.xkd.mapper.TDeviceStatusMapper;
import com.ruoyi.xkd.protocol.codec.AntennaFrameCodec;
import com.ruoyi.xkd.protocol.codec.AntennaParamCodec;
import com.ruoyi.xkd.protocol.codec.ByteCodec;
import com.ruoyi.xkd.protocol.constants.AntennaProtocolConstants;
import com.ruoyi.xkd.protocol.model.AntennaFrame;
import com.ruoyi.xkd.protocol.model.AntennaParam;
import com.ruoyi.xkd.service.ITAntennaProtocolLogService;
import com.ruoyi.xkd.udp.AntennaUdpClient;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * CMD_REPORT 0x84 主动上报处理
 */
@Service
public class AntennaReportService
{
    private final AntennaParamCodec paramCodec;

    private final AntennaFrameCodec frameCodec;

    private final AntennaUdpClient udpClient;

    private final TDeviceStatusMapper tDeviceStatusMapper;

    private final TAlarmLogMapper tAlarmLogMapper;

    private final ITAntennaProtocolLogService antennaProtocolLogService;

    private final SimpMessagingTemplate messagingTemplate;

    public AntennaReportService(AntennaParamCodec paramCodec,
                                AntennaFrameCodec frameCodec,
                                AntennaUdpClient udpClient,
                                TDeviceStatusMapper tDeviceStatusMapper,
                                TAlarmLogMapper tAlarmLogMapper,
                                ITAntennaProtocolLogService antennaProtocolLogService,
                                SimpMessagingTemplate messagingTemplate)
    {
        this.paramCodec = paramCodec;
        this.frameCodec = frameCodec;
        this.udpClient = udpClient;
        this.tDeviceStatusMapper = tDeviceStatusMapper;
        this.tAlarmLogMapper = tAlarmLogMapper;
        this.antennaProtocolLogService = antennaProtocolLogService;
        this.messagingTemplate = messagingTemplate;
    }

    public void handleReport(AntennaFrame frame)
    {
        List<AntennaParam> params = paramCodec.decodeParams(frame.getPayload());

        Map<String, Object> workParams = new HashMap<>();

        String runStatus = "NORMAL";
        boolean needAlarm = false;
        String alarmDesc = null;

        for (AntennaParam param : params)
        {
            int key = param.getKey();
            byte[] value = param.getValue();

            switch (key)
            {
                case AntennaProtocolConstants.KEY_SELF_CHECK_READY:
                    // 0x81 自检就绪
                    // 假设 0 表示成功，非 0 表示失败
                    if (value.length >= 1)
                    {
                        int selfCheck = value[0] & 0xFF;
                        workParams.put("selfCheckReady", selfCheck);

                        if (selfCheck == 0)
                        {
                            runStatus = "NORMAL";
                        }
                        else
                        {
                            runStatus = "ABNORMAL";
                            needAlarm = true;
                            alarmDesc = "天线自检失败，code=" + selfCheck;
                        }
                    }
                    break;

                case AntennaProtocolConstants.KEY_REPORT_TOD:
                    // 0x82 TOD
                    workParams.put("tod", ByteCodec.toHex(value));
                    break;

                case AntennaProtocolConstants.KEY_PARABOLA_REACH:
                    // 0x83 抛物面达到起跟位置
                    workParams.put("reachStartTrack", true);
                    break;

                default:
                    workParams.put("report_0x" + Integer.toHexString(key).toUpperCase(), ByteCodec.toHex(value));
                    break;
            }
        }

        TDeviceStatus status = new TDeviceStatus();

        if (frame.getDeviceId() == null)
        {
            throw new RuntimeException("设备ID为空，无法写入设备状态");
        }

        status.setDeviceId(frame.getDeviceId());
        status.setDeviceCode(frame.getDeviceCode());
        status.setDeviceName(frame.getDeviceName());

        status.setRunStatus(runStatus);
        status.setWorkParams(JSON.toJSONString(workParams));
        status.setCollectTime(new Date());
        status.setReportTime(DateUtils.getNowDate());
        status.setCreateTime(DateUtils.getNowDate());

        tDeviceStatusMapper.insertTDeviceStatus(status);

        WebSocketMessage message = new WebSocketMessage("DEVICE_STATUS_UPDATE", new DeviceStatusPushDTO(status));
        messagingTemplate.convertAndSend("/topic/antenna/status", message);

        if (needAlarm)
        {
            insertDeviceAlarm(frame.getDeviceCode(), alarmDesc);
        }

        sendReportAck(frame, AntennaProtocolConstants.PARAM_SET_OK);
    }

    private void sendReportAck(AntennaFrame frame, int status)
    {
        try
        {
            byte[] payload = new byte[] { (byte) status };
            byte[] ackFrame = frameCodec.encode(AntennaProtocolConstants.CMD_REPORT_ACK, payload);
            udpClient.send(frame.getRemoteIp(), frame.getRemotePort(), ackFrame);

            writeReportAckProtocolLog(frame, status, ackFrame, payload);
        }
        catch (Exception e)
        {
            throw new RuntimeException("发送主动上报 ACK 失败", e);
        }
    }

    private void writeReportAckProtocolLog(AntennaFrame frame, int status, byte[] ackFrame, byte[] payload)
    {
        try
        {
            TAntennaProtocolLog log = new TAntennaProtocolLog();

            log.setDeviceCode(frame.getDeviceCode());
            log.setDirection("SEND");
            log.setRemoteIp(frame.getRemoteIp());
            log.setRemotePort(frame.getRemotePort() != null ? frame.getRemotePort().longValue() : null);
            log.setCmdCode(String.format("0x%02X", AntennaProtocolConstants.CMD_REPORT_ACK));
            log.setCmdName("CMD_REPORT_ACK");
            log.setFrameHex(ByteCodec.toHex(ackFrame));
            log.setPayloadHex(ByteCodec.toHex(payload));
            log.setCheckStatus("OK");
            log.setResultStatus("SUCCESS");
            log.setCreateTime(DateUtils.getNowDate());

            antennaProtocolLogService.insertTAntennaProtocolLog(log);
        }
        catch (Exception e)
        {
            // 记录日志写入失败，但不影响主要业务
        }
    }

    private void insertDeviceAlarm(String deviceCode, String desc)
    {
        TAlarmLog alarm = new TAlarmLog();
        alarm.setAlarmType("DEVICE");
        alarm.setAlarmLevel("ERROR");
        alarm.setAlarmDesc(desc);
        alarm.setAlarmLocation(deviceCode);
        alarm.setRelatedId(deviceCode);
        alarm.setOccurTime(DateUtils.getNowDate());
        alarm.setHandleStatus("0");
        alarm.setCreateTime(DateUtils.getNowDate());

        tAlarmLogMapper.insertTAlarmLog(alarm);
    }
}