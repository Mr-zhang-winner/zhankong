package com.ruoyi.xkd.protocol.model.union;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 8字节双精度浮点数联合体
 * 对应C语言的 DOUBLE_UNION
 * 用于协议数据的字节序转换，采用大端序（Big Endian）
 */
public class DoubleUnion {
    /** 存储双精度浮点数值 */
    private double a;

    /** 默认构造函数 */
    public DoubleUnion() {}

    /**
     * 带初始值的构造函数
     * @param value 初始值
     */
    public DoubleUnion(double value) {
        this.a = value;
    }

    /**
     * 获取双精度浮点数值
     * @return 双精度浮点数
     */
    public double getA() {
        return a;
    }

    /**
     * 设置双精度浮点数值
     * @param a 双精度浮点数
     */
    public void setA(double a) {
        this.a = a;
    }

    /**
     * 将值转换为字节数组（大端序）
     * 使用Java NIO ByteBuffer进行字节序转换
     * @return 8字节数组
     */
    public byte[] getBytes() {
        return ByteBuffer.allocate(8)
                .order(ByteOrder.BIG_ENDIAN)  // 设置大端序
                .putDouble(a)                  // 写入double值
                .array();                      // 获取字节数组
    }

    /**
     * 从字节数组设置值（大端序）
     * 使用Java NIO ByteBuffer进行字节序转换
     * @param b 8字节数组
     */
    public void setBytes(byte[] b) {
        if (b != null && b.length >= 8) {
            this.a = ByteBuffer.wrap(b, 0, 8)  // 包装字节数组
                    .order(ByteOrder.BIG_ENDIAN)  // 设置大端序
                    .getDouble();                 // 读取double值
        }
    }
}