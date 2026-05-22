package com.ruoyi.xkd.protocol.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.xkd.domain.dto.ProtocolParseResult;
import com.ruoyi.xkd.protocol.codec.AntennaParamCodec;
import com.ruoyi.xkd.protocol.constants.AntennaProtocolConstants;
import com.ruoyi.xkd.protocol.model.AntennaFrame;
import com.ruoyi.xkd.protocol.model.FactoryParam;

@Service
public class AntennaFactoryAckService
{
    private final AntennaParamCodec paramCodec;
    private final SimpMessagingTemplate messagingTemplate;

    public AntennaFactoryAckService(AntennaParamCodec paramCodec,
                                    SimpMessagingTemplate messagingTemplate)
    {
        this.paramCodec = paramCodec;
        this.messagingTemplate = messagingTemplate;
    }

    public void handleFactoryAck(AntennaFrame frame)
    {
        // 使用 FactoryParam 结构体解析出厂信息
        FactoryParam factory = paramCodec.decodeFactoryParam(frame.getPayload());

        // 获取设备出厂信息
        String deviceName = factory.getStrDeviceName();
        String deviceOem = factory.getStrDeviceOem();
        String deviceSn = factory.getStrDeviceSn();
        String deviceDate = factory.getStrDeviceDate();
        String softVersion = factory.getStrSoftVersion();
        int deviceInfoValid = factory.getDeviceInfoValid();

        System.out.println("设备名称: " + deviceName);
        System.out.println("设备制造商: " + deviceOem);
        System.out.println("设备序列号: " + deviceSn);
        System.out.println("生产日期: " + deviceDate);
        System.out.println("软件版本: " + softVersion);
        System.out.println("信息有效性: " + (deviceInfoValid == 1 ? "有效" : "无效"));

        // WebSocket 推送出厂信息解析结果
        ProtocolParseResult parseResult = new ProtocolParseResult();
        parseResult.setCmdCode(String.format("0x%02X", AntennaProtocolConstants.CMD_FAC_QUERY_ACK));
        parseResult.setCmdName("CMD_FAC_QUERY_ACK");
        parseResult.setDeviceCode(frame.getDeviceCode());
        parseResult.setDirection("RECEIVE");
        parseResult.setRemoteIp(frame.getRemoteIp());
        parseResult.setRemotePort(frame.getRemotePort());
        parseResult.setParseTime(new Date());
        parseResult.setCheckStatus("OK");
        parseResult.setFrameHex("");
        parseResult.setPayloadHex("");
        
        Map<String, Object> factoryParams = new HashMap<>();
        factoryParams.put("deviceName", deviceName);
        factoryParams.put("deviceOem", deviceOem);
        factoryParams.put("deviceSn", deviceSn);
        factoryParams.put("deviceDate", deviceDate);
        factoryParams.put("softVersion", softVersion);
        factoryParams.put("deviceInfoValid", deviceInfoValid);
        parseResult.setParams(factoryParams);
        
        messagingTemplate.convertAndSend("/topic/antenna/factory", parseResult);
    }
}