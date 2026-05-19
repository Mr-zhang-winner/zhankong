package com.ruoyi.xkd.protocol.service;

import org.springframework.stereotype.Service;

import com.ruoyi.xkd.protocol.model.AntennaFrame;

@Service
public class AntennaBroadcastAckService
{
    public void handleBroadcastAck(AntennaFrame frame)
    {
        byte[] payload = frame.getPayload();

        if (payload == null || payload.length < 8)
        {
            return;
        }

        int index = 0;

        int antennaType = payload[index++] & 0xFF;
        int subType = payload[index++] & 0xFF;
        int addrType = payload[index++] & 0xFF;
        int frameSeq = payload[index++] & 0xFF;

        int connType = payload[index++] & 0xFF;

        byte[] ipBytes = new byte[4];
        System.arraycopy(payload, index, ipBytes, 0, 4);
        index += 4;

        String deviceIp = String.format("%d.%d.%d.%d",
                ipBytes[0] & 0xFF, ipBytes[1] & 0xFF,
                ipBytes[2] & 0xFF, ipBytes[3] & 0xFF);

        if (index + 4 <= payload.length)
        {
            byte[] maskBytes = new byte[4];
            System.arraycopy(payload, index, maskBytes, 0, 4);
            index += 4;
        }

        int devicePort = -1;
        if (index + 2 <= payload.length)
        {
            devicePort = ((payload[index] & 0xFF) << 8) | (payload[index + 1] & 0xFF);
        }
    }
}