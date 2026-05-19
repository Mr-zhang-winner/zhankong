package com.ruoyi.xkd.protocol.service;

import org.springframework.stereotype.Service;

import com.ruoyi.xkd.protocol.codec.AntennaParamCodec;
import com.ruoyi.xkd.protocol.constants.AntennaProtocolConstants;
import com.ruoyi.xkd.protocol.model.AntennaFrame;
import com.ruoyi.xkd.protocol.model.EphesParam;

@Service
public class AntennaEphemerisAckService
{
    private final AntennaParamCodec paramCodec;

    public AntennaEphemerisAckService(AntennaParamCodec paramCodec)
    {
        this.paramCodec = paramCodec;
    }

    public void handleEphemerisAck(AntennaFrame frame)
    {
        byte[] payload = frame.getPayload();

        if (payload == null || payload.length < 1)
        {
            throw new IllegalArgumentException("星历 ACK 参数体为空");
        }

        int status = payload[0] & 0xFF;

        if (status == AntennaProtocolConstants.PARAM_SET_OK)
        {
            // 星历设置成功，可以记录日志或更新状态
        }
        else
        {
            // 星历设置失败
        }
    }

    /**
     * 解析星历数据（CMD_EPHES_SET_ACK 的响应处理）
     */
    public void handleEphemerisData(AntennaFrame frame)
    {
        // 使用 EphesParam 结构体解析星历数据
        EphesParam ephes = paramCodec.decodeEphesParam(frame.getPayload());

        // 获取星历信息
        int satId = ephes.getSatId().getA();
        int dataNum = ephes.getDataNum().getA();
        
        // 获取 GPS 周数和秒数数组（最多20个历元点）
        for (int i = 0; i < Math.min(dataNum, 20); i++) {
            long gpsWeek = ephes.getGpsWeek()[i].getA();
            long gpsSecond = ephes.getGpsSecond()[i].getA();
            double x = ephes.getWgs84X()[i].getA();
            double y = ephes.getWgs84Y()[i].getA();
            double z = ephes.getWgs84Z()[i].getA();
            
            // 处理每个历元点的数据
            System.out.println("历元点[" + i + "]: GPS周=" + gpsWeek + ", GPS秒=" + gpsSecond 
                + ", X=" + x + ", Y=" + y + ", Z=" + z);
        }

        // 获取速度信息
        double vx = ephes.getWgs84Vx().getA();
        double vy = ephes.getWgs84Vy().getA();
        double vz = ephes.getWgs84Vz().getA();

        // 可以将星历数据保存到数据库或进行其他业务处理
    }
}
