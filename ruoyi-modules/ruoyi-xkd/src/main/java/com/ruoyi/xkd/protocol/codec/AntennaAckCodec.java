package com.ruoyi.xkd.protocol.codec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ruoyi.xkd.protocol.model.AntennaAckItem;

@Component
public class AntennaAckCodec
{
    public List<AntennaAckItem> decodeSetAck(byte[] payload)
    {
        List<AntennaAckItem> list = new ArrayList<>();

        if (payload == null || payload.length == 0)
        {
            return list;
        }

        if (payload.length % 2 != 0)
        {
            throw new IllegalArgumentException("SET_ACK 参数体长度必须为偶数");
        }

        for (int i = 0; i < payload.length; i += 2)
        {
            AntennaAckItem item = new AntennaAckItem();
            item.setKey(payload[i] & 0xFF);
            item.setStatus(payload[i + 1] & 0xFF);
            list.add(item);
        }

        return list;
    }
}