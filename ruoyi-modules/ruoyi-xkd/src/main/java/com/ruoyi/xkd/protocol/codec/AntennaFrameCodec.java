package com.ruoyi.xkd.protocol.codec;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.ruoyi.xkd.protocol.constants.AntennaProtocolConstants;
import com.ruoyi.xkd.protocol.model.AntennaFrame;

@Component
public class AntennaFrameCodec
{
    public byte[] encode(int cmd, byte[] payload)
    {
        if (payload == null)
        {
            payload = new byte[0];
        }

        int length = 1 + payload.length;

        ByteArrayOutputStream plain = new ByteArrayOutputStream();
        plain.write(AntennaProtocolConstants.FRAME_FLAG);
        plain.write((length >> 8) & 0xFF);
        plain.write(length & 0xFF);
        plain.write(cmd & 0xFF);
        plain.write(payload, 0, payload.length);

        int checksum = checksum(cmd, payload);
        plain.write(checksum);

        byte[] plainBytes = plain.toByteArray();

        ByteArrayOutputStream escaped = new ByteArrayOutputStream();
        escaped.write(AntennaProtocolConstants.FRAME_FLAG);

        for (int i = 1; i < plainBytes.length; i++)
        {
            int value = plainBytes[i] & 0xFF;

            if (value == AntennaProtocolConstants.FRAME_FLAG)
            {
                escaped.write(AntennaProtocolConstants.ESCAPE);
                escaped.write(AntennaProtocolConstants.ESCAPE_7E);
            }
            else if (value == AntennaProtocolConstants.ESCAPE)
            {
                escaped.write(AntennaProtocolConstants.ESCAPE);
                escaped.write(AntennaProtocolConstants.ESCAPE_7D);
            }
            else
            {
                escaped.write(value);
            }
        }

        escaped.write(AntennaProtocolConstants.FRAME_FLAG);

        return escaped.toByteArray();
    }

    public AntennaFrame decode(byte[] data, int length)
    {
        if (data == null || length < 6)
        {
            throw new IllegalArgumentException("协议帧长度不足");
        }

        byte[] raw = Arrays.copyOf(data, length);

        if ((raw[0] & 0xFF) != AntennaProtocolConstants.FRAME_FLAG
                || (raw[length - 1] & 0xFF) != AntennaProtocolConstants.FRAME_FLAG)
        {
            throw new IllegalArgumentException("协议帧起止符错误");
        }

        byte[] unescaped = unescape(raw, length);

        int frameLength = ByteCodec.readU16(unescaped, 1);
        int cmd = unescaped[3] & 0xFF;

        if (frameLength < 1)
        {
            throw new IllegalArgumentException("协议帧长度字段错误");
        }

        int payloadLength = frameLength - 1;
        int payloadStart = 4;
        int payloadEnd = payloadStart + payloadLength;

        if (payloadEnd >= unescaped.length)
        {
            throw new IllegalArgumentException("协议帧参数体长度越界");
        }

        byte[] payload = Arrays.copyOfRange(unescaped, payloadStart, payloadEnd);
        int actualChecksum = unescaped[payloadEnd] & 0xFF;
        int expectedChecksum = checksum(cmd, payload);

        if (actualChecksum != expectedChecksum)
        {
            throw new IllegalArgumentException("协议帧校验失败，expected="
                    + expectedChecksum + ", actual=" + actualChecksum);
        }

        AntennaFrame frame = new AntennaFrame();
        frame.setCmd(cmd);
        frame.setLength(frameLength);
        frame.setPayload(payload);
        frame.setChecksum(actualChecksum);
        frame.setRawFrame(raw);

        return frame;
    }

    private byte[] unescape(byte[] raw, int length)
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        out.write(raw[0]);

        for (int i = 1; i < length - 1; i++)
        {
            int value = raw[i] & 0xFF;

            if (value == AntennaProtocolConstants.ESCAPE)
            {
                if (i + 1 >= length - 1)
                {
                    throw new IllegalArgumentException("转义字节不完整");
                }

                int next = raw[++i] & 0xFF;

                if (next == AntennaProtocolConstants.ESCAPE_7E)
                {
                    out.write(AntennaProtocolConstants.FRAME_FLAG);
                }
                else if (next == AntennaProtocolConstants.ESCAPE_7D)
                {
                    out.write(AntennaProtocolConstants.ESCAPE);
                }
                else
                {
                    throw new IllegalArgumentException("非法转义序列：7D "
                            + String.format("%02X", next));
                }
            }
            else
            {
                out.write(value);
            }
        }

        out.write(raw[length - 1]);

        return out.toByteArray();
    }

    private int checksum(int cmd, byte[] payload)
    {
        int sum = cmd & 0xFF;

        if (payload != null)
        {
            for (byte b : payload)
            {
                sum += b & 0xFF;
            }
        }

        return sum & 0xFF;
    }
}