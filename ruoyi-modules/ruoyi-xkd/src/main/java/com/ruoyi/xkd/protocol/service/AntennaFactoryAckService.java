package com.ruoyi.xkd.protocol.service;

import org.springframework.stereotype.Service;

import com.ruoyi.xkd.protocol.codec.AntennaParamCodec;
import com.ruoyi.xkd.protocol.model.AntennaFrame;
import com.ruoyi.xkd.protocol.model.FactoryParam;

@Service
public class AntennaFactoryAckService
{
    private final AntennaParamCodec paramCodec;

    public AntennaFactoryAckService(AntennaParamCodec paramCodec)
    {
        this.paramCodec = paramCodec;
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

        // 可以将这些信息保存到数据库或进行其他业务处理
        // 例如：更新设备配置信息
        System.out.println("设备名称: " + deviceName);
        System.out.println("设备制造商: " + deviceOem);
        System.out.println("设备序列号: " + deviceSn);
        System.out.println("生产日期: " + deviceDate);
        System.out.println("软件版本: " + softVersion);
        System.out.println("信息有效性: " + (deviceInfoValid == 1 ? "有效" : "无效"));
    }
}
