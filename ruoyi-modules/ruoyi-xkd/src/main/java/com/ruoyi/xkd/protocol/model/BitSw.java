package com.ruoyi.xkd.protocol.model;

/**
 * 天线开关状态位域结构
 * 对应C语言的 BIT_SW
 * 用于存储天线的开关状态信息，采用位域方式存储在1字节中
 * 
 * 位域布局（从低到高）：
 * - bit[1:0]: sw_tx    - 发射开关状态
 * - bit[3:2]: sw_rx    - 接收开关状态  
 * - bit[5:4]: sw_follow - 跟踪开关状态
 * - bit[7:6]: reserve  - 保留位
 */
public class BitSw {
    /** 存储位域值的原始字节 */
    private int value;

    /** 默认构造函数 */
    public BitSw() {}

    /**
     * 带初始值的构造函数
     * @param value 初始位域值
     */
    public BitSw(int value) {
        // 确保只保留低8位
        this.value = value & 0xFF;
    }

    /**
     * 获取发射开关状态
     * @return 发射开关状态（0-3）
     */
    public int getSwTx() {
        // 提取bit[1:0]
        return (value >> 0) & 0x03;
    }

    /**
     * 设置发射开关状态
     * @param swTx 发射开关状态（0-3）
     */
    public void setSwTx(int swTx) {
        // 清除bit[1:0]，然后设置新值
        value = (value & ~0x03) | (swTx & 0x03);
    }

    /**
     * 获取接收开关状态
     * @return 接收开关状态（0-3）
     */
    public int getSwRx() {
        // 提取bit[3:2]
        return (value >> 2) & 0x03;
    }

    /**
     * 设置接收开关状态
     * @param swRx 接收开关状态（0-3）
     */
    public void setSwRx(int swRx) {
        // 清除bit[3:2]，然后设置新值
        value = (value & ~0x0C) | ((swRx & 0x03) << 2);
    }

    /**
     * 获取跟踪开关状态
     * @return 跟踪开关状态（0-3）
     */
    public int getSwFollow() {
        // 提取bit[5:4]
        return (value >> 4) & 0x03;
    }

    /**
     * 设置跟踪开关状态
     * @param swFollow 跟踪开关状态（0-3）
     */
    public void setSwFollow(int swFollow) {
        // 清除bit[5:4]，然后设置新值
        value = (value & ~0x30) | ((swFollow & 0x03) << 4);
    }

    /**
     * 获取保留位值
     * @return 保留位值（0-3）
     */
    public int getReserve() {
        // 提取bit[7:6]
        return (value >> 6) & 0x03;
    }

    /**
     * 设置保留位值
     * @param reserve 保留位值（0-3）
     */
    public void setReserve(int reserve) {
        // 清除bit[7:6]，然后设置新值
        value = (value & ~0xC0) | ((reserve & 0x03) << 6);
    }

    /**
     * 获取原始位域值
     * @return 原始字节值
     */
    public int getValue() {
        return value & 0xFF;
    }

    /**
     * 设置原始位域值
     * @param value 原始字节值
     */
    public void setValue(int value) {
        this.value = value & 0xFF;
    }

    /**
     * 将位域值转换为字节数组
     * @return 1字节数组
     */
    public byte[] getBytes() {
        return new byte[] {(byte) (value & 0xFF)};
    }

    /**
     * 从字节数组设置位域值
     * @param b 1字节数组
     */
    public void setBytes(byte[] b) {
        if (b != null && b.length > 0) {
            this.value = b[0] & 0xFF;
        }
    }
}