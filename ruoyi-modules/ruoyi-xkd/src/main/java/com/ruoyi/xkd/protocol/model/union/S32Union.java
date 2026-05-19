package com.ruoyi.xkd.protocol.model.union;

/**
 * 4字节有符号整数联合体
 * 对应C语言的 S32_UNION
 * 用于协议数据的字节序转换，采用大端序（Big Endian）
 */
public class S32Union {
    /** 存储有符号32位整数的值 */
    private int a;

    /** 默认构造函数 */
    public S32Union() {}

    /**
     * 带初始值的构造函数
     * @param value 初始值
     */
    public S32Union(int value) {
        this.a = value;
    }

    /**
     * 获取有符号32位整数值
     * @return 有符号32位整数
     */
    public int getA() {
        return a;
    }

    /**
     * 设置有符号32位整数值
     * @param a 有符号32位整数
     */
    public void setA(int a) {
        this.a = a;
    }

    /**
     * 将值转换为字节数组（大端序）
     * 按字节从高到低排列
     * @return 4字节数组
     */
    public byte[] getBytes() {
        return new byte[] {
            (byte) ((a >> 24) & 0xFF),  // 第1字节（最高位，含符号位）
            (byte) ((a >> 16) & 0xFF),  // 第2字节
            (byte) ((a >> 8) & 0xFF),   // 第3字节
            (byte) (a & 0xFF)            // 第4字节（最低位）
        };
    }

    /**
     * 从字节数组设置值（大端序）
     * @param b 4字节数组
     */
    public void setBytes(byte[] b) {
        if (b != null && b.length >= 4) {
            // 按字节从高到低依次左移并进行或运算，自动保留符号位
            this.a = ((b[0] & 0xFF) << 24)
                    | ((b[1] & 0xFF) << 16)
                    | ((b[2] & 0xFF) << 8)
                    | (b[3] & 0xFF);
        }
    }
}