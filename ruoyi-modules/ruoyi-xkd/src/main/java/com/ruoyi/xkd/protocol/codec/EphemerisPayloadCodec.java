package com.ruoyi.xkd.protocol.codec;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.xkd.protocol.constants.AntennaProtocolConstants;
import com.ruoyi.xkd.protocol.model.EphemerisPoint;

@Component
public class EphemerisPayloadCodec
{
    public byte[] encode(int satId, int totalNum, List<EphemerisPoint> points)
    {
        if (points == null || points.isEmpty())
        {
            throw new ServiceException("星历点不能为空");
        }

        if (points.size() > AntennaProtocolConstants.MAX_EPHES_POINTS_PER_PACKET)
        {
            throw new ServiceException("单包星历点数不能超过 "
                    + AntennaProtocolConstants.MAX_EPHES_POINTS_PER_PACKET);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        write(out, ByteCodec.u16(satId));
        write(out, ByteCodec.u16(totalNum));
        out.write(points.size() & 0xFF);

        for (EphemerisPoint p : points)
        {
            write(out, ByteCodec.u32(p.getGpsWeek()));
            write(out, ByteCodec.u32(p.getGpsSecond()));
            write(out, ByteCodec.f64(p.getX()));
            write(out, ByteCodec.f64(p.getY()));
            write(out, ByteCodec.f64(p.getZ()));
            write(out, ByteCodec.f64(p.getVx()));
            write(out, ByteCodec.f64(p.getVy()));
            write(out, ByteCodec.f64(p.getVz()));
            write(out, ByteCodec.u16(p.getDataSeq()));
        }

        return out.toByteArray();
    }

    private void write(ByteArrayOutputStream out, byte[] data)
    {
        out.write(data, 0, data.length);
    }
}