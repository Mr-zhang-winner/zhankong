package com.ruoyi.xkd.protocol.codec;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteCodec
{
    private ByteCodec() {}

    public static byte[] u16(int value)
    {
        return new byte[] {
            (byte) ((value >> 8) & 0xFF),
            (byte) (value & 0xFF)
        };
    }

    public static byte[] u32(long value)
    {
        return new byte[] {
            (byte) ((value >> 24) & 0xFF),
            (byte) ((value >> 16) & 0xFF),
            (byte) ((value >> 8) & 0xFF),
            (byte) (value & 0xFF)
        };
    }

    public static byte[] i16(int value)
    {
        return u16(value);
    }

    public static byte[] i32(int value)
    {
        return u32(value);
    }

    public static byte[] f64(double value)
    {
        return ByteBuffer.allocate(8)
                .order(ByteOrder.BIG_ENDIAN)
                .putDouble(value)
                .array();
    }

    public static int readU16(byte[] data, int offset)
    {
        return ((data[offset] & 0xFF) << 8)
                | (data[offset + 1] & 0xFF);
    }

    public static long readU32(byte[] data, int offset)
    {
        return ((long) (data[offset] & 0xFF) << 24)
                | ((long) (data[offset + 1] & 0xFF) << 16)
                | ((long) (data[offset + 2] & 0xFF) << 8)
                | ((long) (data[offset + 3] & 0xFF));
    }

    public static double readF64(byte[] data, int offset)
    {
        return ByteBuffer.wrap(data, offset, 8)
                .order(ByteOrder.BIG_ENDIAN)
                .getDouble();
    }

    public static String toHex(byte[] data)
    {
        if (data == null)
        {
            return "";
        }

        return toHex(data, 0, data.length);
    }

    public static String toHex(byte[] data, int offset, int length)
    {
        if (data == null || length == 0)
        {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = offset; i < offset + length; i++)
        {
            sb.append(String.format("%02X", data[i]));
        }

        return sb.toString();
    }
}