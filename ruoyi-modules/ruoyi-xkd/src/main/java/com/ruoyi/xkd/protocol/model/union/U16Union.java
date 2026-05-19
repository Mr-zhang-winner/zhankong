package com.ruoyi.xkd.protocol.model.union;

/**
 * 2字节无符号整数联合体
 * 对应C语言的 U16_UNION
 * 用于协议数据的字节序转换，采用大端序（Big Endian）
 */
public class U16Union {
    /** 存储无符号16位整数的值 */
    private int a;

    /** 默认构造函数 */
    public U16Union() {}

    /**
     * 带初始值的构造函数
     * @param value 初始值
     */
    public U16Union(int value) {
        // 确保只保留低16位
        this.a = value & 0xFFFF;
    }

    /**
     * 获取无符号16位整数值
     * @return 无符号16位整数
     */
    public int getA() {
        return a & 0xFFFF;
    }

    /**
     * 设置无符号16位整数值
     * @param a 无符号16位整数
     */
    public void setA(int a) {
        // 确保只保留低16位
        this.a = a & 0xFFFF;
    }

    /**
     * 将值转换为字节数组（大端序）
     * 高字节在前，低字节在后
     * @return 2字节数组
     */
    public byte[] getBytes() {
        return new byte[] {
            (byte) ((a >> 8) & 0xFF),  // 高字节
            (byte) (a & 0xFF)           // 低字节
        };
    }

    /**
     * 从字节数组设置值（大端序）
     * @param b 2字节数组
     */
    public void setBytes(byte[] b) {
        if (b != null && b.length >= 2) {
            // 高字节左移8位，与低字节进行或运算
            this.a = ((b[0] & 0xFF) << 8) | (b[1] & 0xFF);
        }
    }
}