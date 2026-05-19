package com.ruoyi.xkd.protocol.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Service;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.xkd.domain.TAntennaProtocolLog;
import com.ruoyi.xkd.domain.TDeviceConfig;
import com.ruoyi.xkd.domain.TDeviceControlLog;
import com.ruoyi.xkd.mapper.TDeviceConfigMapper;
import com.ruoyi.xkd.mapper.TDeviceControlLogMapper;
import com.ruoyi.xkd.protocol.codec.AntennaFrameCodec;
import com.ruoyi.xkd.protocol.codec.ByteCodec;
import com.ruoyi.xkd.protocol.constants.AntennaProtocolConstants;
import com.ruoyi.xkd.service.ITAntennaProtocolLogService;
import com.ruoyi.xkd.udp.AntennaUdpClient;

@Service
public class AntennaProtocolSendService
{
    private final AntennaFrameCodec frameCodec;
    private final AntennaUdpClient udpClient;
    private final TDeviceControlLogMapper tDeviceControlLogMapper;
    private final TDeviceConfigMapper tDeviceConfigMapper;
    private final ITAntennaProtocolLogService antennaProtocolLogService;

    public AntennaProtocolSendService(AntennaFrameCodec frameCodec,
                                      AntennaUdpClient udpClient,
                                      TDeviceControlLogMapper tDeviceControlLogMapper,
                                      TDeviceConfigMapper tDeviceConfigMapper,
                                      ITAntennaProtocolLogService antennaProtocolLogService)
    {
        this.frameCodec = frameCodec;
        this.udpClient = udpClient;
        this.tDeviceControlLogMapper = tDeviceControlLogMapper;
        this.tDeviceConfigMapper = tDeviceConfigMapper;
        this.antennaProtocolLogService = antennaProtocolLogService;
    }

    public void send(String deviceCode, String ip, Integer port, int cmd, byte[] payload)
    {
        byte[] frame = frameCodec.encode(cmd, payload);

        TDeviceControlLog log = buildControlLog(deviceCode, cmd, payload, frame);
        tDeviceControlLogMapper.insertTDeviceControlLog(log);

        TAntennaProtocolLog protocolLog = buildSendProtocolLog(deviceCode, ip, port, cmd, payload, frame);
        antennaProtocolLogService.insertTAntennaProtocolLog(protocolLog);

        try
        {
            udpClient.send(ip, port, frame);
        }
        catch (Exception e)
        {
            log.setSendStatus("FAILED");
            log.setErrorMsg(e.getMessage());
            tDeviceControlLogMapper.updateTDeviceControlLog(log);

            throw new RuntimeException("发送天线协议命令失败：" + e.getMessage(), e);
        }
    }

    public void broadcast(int cmd, byte[] payload)
    {
        try
        {
            byte[] frame = frameCodec.encode(cmd, payload);
            udpClient.broadcast(54000, frame);

            writeBroadcastProtocolLog(cmd, payload, frame);
        }
        catch (Exception e)
        {
            throw new RuntimeException("发送广播搜索命令失败：" + e.getMessage(), e);
        }
    }

    private void writeBroadcastProtocolLog(int cmd, byte[] payload, byte[] frame)
    {
        try
        {
            TAntennaProtocolLog log = new TAntennaProtocolLog();

            log.setDeviceCode("BROADCAST");
            log.setDirection("SEND");
            log.setRemoteIp("255.255.255.255");
            log.setRemotePort(54000L);
            log.setCmdCode(toHex(cmd));
            log.setCmdName(cmdName(cmd));
            log.setFrameHex(ByteCodec.toHex(frame));
            log.setPayloadHex(ByteCodec.toHex(payload));
            log.setCheckStatus("OK");
            log.setResultStatus("SUCCESS");
            log.setCreateTime(DateUtils.getNowDate());

            antennaProtocolLogService.insertTAntennaProtocolLog(log);
        }
        catch (Exception e)
        {
            // 日志写入失败不影响主流程
        }
    }

    private TDeviceControlLog buildControlLog(String deviceCode, int cmd, byte[] payload, byte[] frame)
    {
        TDeviceControlLog log = new TDeviceControlLog();

        TDeviceConfig deviceConfig = findDeviceConfig(deviceCode);

        if (deviceConfig != null)
        {
            log.setDeviceId(deviceConfig.getDeviceId());
            log.setDeviceCode(deviceConfig.getDeviceCode());
        }
        else
        {
            log.setDeviceCode(deviceCode);
        }

        log.setCommandType(cmdName(cmd));
        log.setCommandKey(firstCommandKey(payload));

        Map<String, Object> commandContent = new HashMap<>();
        commandContent.put("payloadHex", ByteCodec.toHex(payload));
        log.setCommandContent(JSON.toJSONString(commandContent));

        log.setCmdCode(toHex(cmd));
        log.setFrameHex(ByteCodec.toHex(frame));

        log.setSendStatus("WAITING");
        log.setSendTime(DateUtils.getNowDate());
        log.setCreateTime(DateUtils.getNowDate());

        return log;
    }

    private TDeviceConfig findDeviceConfig(String deviceCode)
    {
        if (deviceCode == null || deviceCode.isEmpty())
        {
            return null;
        }

        TDeviceConfig query = new TDeviceConfig();
        query.setDeviceCode(deviceCode);

        List<TDeviceConfig> list = tDeviceConfigMapper.selectTDeviceConfigList(query);

        if (list != null && !list.isEmpty())
        {
            return list.get(0);
        }

        return null;
    }

    private String firstCommandKey(byte[] payload)
    {
        if (payload == null || payload.length == 0)
        {
            return null;
        }

        return toHex(payload[0] & 0xFF);
    }

    private String toHex(int value)
    {
        return String.format("0x%02X", value);
    }

    private String cmdName(int cmd)
    {
        if (cmd == AntennaProtocolConstants.CMD_SET)
        {
            return "CMD_SET";
        }
        if (cmd == AntennaProtocolConstants.CMD_QUERY)
        {
            return "CMD_QUERY";
        }
        if (cmd == AntennaProtocolConstants.CMD_FAC_QUERY)
        {
            return "CMD_FAC_QUERY";
        }
        if (cmd == AntennaProtocolConstants.CMD_BRD_SEARCH)
        {
            return "CMD_BRD_SEARCH";
        }
        if (cmd == AntennaProtocolConstants.CMD_EPHES_SET)
        {
            return "CMD_EPHES_SET";
        }

        return "CMD_" + toHex(cmd);
    }

    private TAntennaProtocolLog buildSendProtocolLog(String deviceCode, String ip, Integer port, int cmd, byte[] payload, byte[] frame)
    {
        TAntennaProtocolLog log = new TAntennaProtocolLog();

        TDeviceConfig deviceConfig = findDeviceConfig(deviceCode);
        if (deviceConfig != null)
        {
            log.setDeviceCode(deviceConfig.getDeviceCode());
        }
        else
        {
            log.setDeviceCode(deviceCode);
        }

        log.setDirection("SEND");
        log.setRemoteIp(ip);
        log.setRemotePort(port != null ? port.longValue() : null);
        log.setCmdCode(toHex(cmd));
        log.setCmdName(cmdName(cmd));
        log.setFrameHex(ByteCodec.toHex(frame));
        log.setPayloadHex(ByteCodec.toHex(payload));
        log.setCheckStatus("OK");
        log.setResultStatus("SUCCESS");
        log.setCreateTime(DateUtils.getNowDate());

        return log;
    }
}