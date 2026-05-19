package com.ruoyi.xkd.protocol.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Service;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.xkd.domain.TAlarmLog;
import com.ruoyi.xkd.domain.TDeviceStatus;
import com.ruoyi.xkd.domain.dto.DeviceStatusPushDTO;
import com.ruoyi.xkd.domain.dto.WebSocketMessage;
import com.ruoyi.xkd.mapper.TAlarmLogMapper;
import com.ruoyi.xkd.mapper.TDeviceStatusMapper;
import com.ruoyi.xkd.protocol.codec.AntennaParamCodec;
import com.ruoyi.xkd.protocol.model.AntennaFrame;
import com.ruoyi.xkd.protocol.model.QueryParam;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
/**
 * CMD_QUERY_ACK 0x83 查询响应处理
 */
@Service
public class AntennaQueryAckService
{
    private final AntennaParamCodec paramCodec;
    private final TDeviceStatusMapper tDeviceStatusMapper;
    private final TAlarmLogMapper tAlarmLogMapper;
    private final SimpMessagingTemplate messagingTemplate;

    public AntennaQueryAckService(AntennaParamCodec paramCodec,
                                  TDeviceStatusMapper tDeviceStatusMapper,
                                  TAlarmLogMapper tAlarmLogMapper,
                                  SimpMessagingTemplate messagingTemplate)
    {
        this.paramCodec = paramCodec;
        this.tDeviceStatusMapper = tDeviceStatusMapper;
        this.tAlarmLogMapper = tAlarmLogMapper;
        this.messagingTemplate = messagingTemplate;
    }

    public void handleQueryAck(AntennaFrame frame)
    {
        // 使用结构体解析查询响应
        QueryParam params = paramCodec.decodeQueryParam(frame.getPayload());

        TDeviceStatus status = new TDeviceStatus();
        Map<String, Object> workParams = new HashMap<>();
        String runStatus = "NORMAL";
        Integer antStatus = 0;
        Integer antParStatus = 0;

        // 从结构体中提取参数
        if (params.getAntAttitudeT() != null && params.getAntAttitudeT().getKey() != 0) {
            int azRaw = params.getAntAttitudeT().getAntAzimuth().getA();
            int elRaw = params.getAntAttitudeT().getAntPitch().getA();
            BigDecimal azimuth = BigDecimal.valueOf(azRaw).divide(BigDecimal.valueOf(100));
            BigDecimal elevation = BigDecimal.valueOf(elRaw).divide(BigDecimal.valueOf(100));
            status.setAzimuth(azimuth);
            status.setElevation(elevation);
            workParams.put("azimuth", azimuth);
            workParams.put("elevation", elevation);
        }

        // KEY_ANT_STATUS (0x0A) 天线状态：0=正常，非0=异常
        if (params.getAntStatusT() != null && params.getAntStatusT().getKey() != 0) {
            antStatus = params.getAntStatusT().getAntStatus();
            workParams.put("antStatus", antStatus);
        }

        if (params.getAntTempT() != null && params.getAntTempT().getKey() != 0) {
            BigDecimal temperature = BigDecimal.valueOf(params.getAntTempT().getAntTemp().getA())
                    .divide(BigDecimal.valueOf(100));
            workParams.put("temperature", temperature);
        }

        if (params.getAntPsrpT() != null && params.getAntPsrpT().getKey() != 0) {
            int rsrp = params.getAntPsrpT().getAntPsrp().getA();
            workParams.put("rsrp", rsrp);
        }

        if (params.getAntSatIdT() != null && params.getAntSatIdT().getKey() != 0) {
            workParams.put("satNo", params.getAntSatIdT().getSatId().getA());
        }

        if (params.getAntTxFreqT() != null && params.getAntTxFreqT().getKey() != 0) {
            workParams.put("txFreq", params.getAntTxFreqT().getAntTxFreq().getA());
        }

        if (params.getAntRxFreqT() != null && params.getAntRxFreqT().getKey() != 0) {
            workParams.put("rxFreq", params.getAntRxFreqT().getAntRxFreq().getA());
        }

        if (params.getAntBucDecayT() != null && params.getAntBucDecayT().getKey() != 0) {
            BigDecimal bucDecay = BigDecimal.valueOf(params.getAntBucDecayT().getAntBucDecay().getA())
                    .divide(BigDecimal.valueOf(100));
            workParams.put("bucDecay", bucDecay);
        }

        if (params.getAntBdcDecayT() != null && params.getAntBdcDecayT().getKey() != 0) {
            BigDecimal bdcDecay = BigDecimal.valueOf(params.getAntBdcDecayT().getAntBdcDecay().getA())
                    .divide(BigDecimal.valueOf(100));
            workParams.put("bdcDecay", bdcDecay);
        }

        if (params.getAntModeT() != null && params.getAntModeT().getKey() != 0) {
            workParams.put("antMode", params.getAntModeT().getAntMode().getA());
        }

        if (params.getAntTxPolT() != null && params.getAntTxPolT().getKey() != 0) {
            workParams.put("txPol", params.getAntTxPolT().getAntTxPol());
        }

        if (params.getAntRxPolT() != null && params.getAntRxPolT().getKey() != 0) {
            workParams.put("rxPol", params.getAntRxPolT().getAntRxPol());
        }

        // 正确的告警判断逻辑：antStatus 为天线状态，非 0 才告警
        if (antStatus != 0) {
            runStatus = "ABNORMAL";
        }

        if (frame.getDeviceId() == null) {
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

        if ("ABNORMAL".equals(runStatus)) {
            insertDeviceAlarm(frame.getDeviceCode(), "天线状态异常，antStatus=" + antStatus);
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
