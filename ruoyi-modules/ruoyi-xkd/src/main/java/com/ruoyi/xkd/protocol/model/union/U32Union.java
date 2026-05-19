package com.ruoyi.xkd.protocol.model.union;

/**
 * 4字节无符号整数联合体
 * 对应C语言的 U32_UNION
 * 用于协议数据的字节序转换，采用大端序（Big Endian）
 */
public class U32Union {
    /** 存储无符号32位整数的值 */
    private long a;

    /** 默认构造函数 */
    public U32Union() {}

    /**
     * 带初始值的构造函数
     * @param value 初始值
     */
    public U32Union(long value) {
        // 确保只保留低32位
        this.a = value & 0xFFFFFFFFL;
    }

    /**
     * 获取无符号32位整数值
     * @return 无符号32位整数
     */
    public long getA() {
        return a & 0xFFFFFFFFL;
    }

    /**
     * 设置无符号32位整数值
     * @param a 无符号32位整数
     */
    public void setA(long a) {
        // 确保只保留低32位
        this.a = a & 0xFFFFFFFFL;
    }

    /**
     * 将值转换为字节数组（大端序）
     * 按字节从高到低排列
     * @return 4字节数组
     */
    public byte[] getBytes() {
        long val = a & 0xFFFFFFFFL;
        return new byte[] {
            (byte) ((val >> 24) & 0xFF),  // 第1字节（最高位）
            (byte) ((val >> 16) & 0xFF),  // 第2字节
            (byte) ((val >> 8) & 0xFF),   // 第3字节
            (byte) (val & 0xFF)            // 第4字节（最低位）
        };
    }

    /**
     * 从字节数组设置值（大端序）
     * @param b 4字节数组
     */
    public void setBytes(byte[] b) {
        if (b != null && b.length >= 4) {
            // 按字节从高到低依次左移并进行或运算
            this.a = ((long) (b[0] & 0xFF) << 24)
                    | ((long) (b[1] & 0xFF) << 16)
                    | ((long) (b[2] & 0xFF) << 8)
                    | ((long) (b[3] & 0xFF));
        }
    }
}