package com.ruoyi.xkd.protocol.model.union;

/**
 * 1字节无符号整数联合体
 * 对应C语言的 U8_UNION
 * 用于协议数据的字节序转换
 */
public class U8Union {
    /** 存储无符号8位整数的值 */
    private int a;

    /** 默认构造函数 */
    public U8Union() {}

    /**
     * 带初始值的构造函数
     * @param value 初始值
     */
    public U8Union(int value) {
        // 确保只保留低8位
        this.a = value & 0xFF;
    }

    /**
     * 获取无符号8位整数值
     * @return 无符号8位整数
     */
    public int getA() {
        return a & 0xFF;
    }

    /**
     * 设置无符号8位整数值
     * @param a 无符号8位整数
     */
    public void setA(int a) {
        // 确保只保留低8位
        this.a = a & 0xFF;
    }

    /**
     * 将值转换为字节数组（大端序）
     * @return 1字节数组
     */
    public byte[] getBytes() {
        return new byte[] {(byte) (a & 0xFF)};
    }

    /**
     * 从字节数组设置值（大端序）
     * @param b 1字节数组
     */
    public void setBytes(byte[] b) {
        if (b != null && b.length > 0) {
            // 取第一个字节
            this.a = b[0] & 0xFF;
        }
    }
}