package com.ruoyi.xkd.protocol.model.union;

/**
 * 2字节有符号整数联合体
 * 对应C语言的 S16_UNION
 * 用于协议数据的字节序转换，采用大端序（Big Endian）
 */
public class S16Union {
    /** 存储有符号16位整数的值 */
    private int a;

    /** 默认构造函数 */
    public S16Union() {}

    /**
     * 带初始值的构造函数
     * @param value 初始值
     */
    public S16Union(int value) {
        this.a = value;
    }

    /**
     * 获取有符号16位整数值
     * @return 有符号16位整数
     */
    public int getA() {
        // 强制转换为short，保留符号位
        return (short) a;
    }

    /**
     * 设置有符号16位整数值
     * @param a 有符号16位整数
     */
    public void setA(int a) {
        this.a = a;
    }

    /**
     * 将值转换为字节数组（大端序）
     * 高字节在前，低字节在后
     * @return 2字节数组
     */
    public byte[] getBytes() {
        int val = (short) a;
        return new byte[] {
            (byte) ((val >> 8) & 0xFF),  // 高字节
            (byte) (val & 0xFF)           // 低字节
        };
    }

    /**
     * 从字节数组设置值（大端序）
     * @param b 2字节数组
     */
    public void setBytes(byte[] b) {
        if (b != null && b.length >= 2) {
            // 高字节左移8位，与低字节进行或运算，强制转换为short保留符号
            this.a = (short) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        }
    }
}