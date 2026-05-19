package com.ruoyi.xkd.protocol.model.param;

import lombok.Data;

/**
 * 增益能力参数
 * 对应C语言的 ANT_CAP_GAIN_T
 * 用于存储天线设备的增益能力信息
 * 
 * 参数格式：key(1字节) + len(1字节) + gain(1字节)
 */
@Data
public class AntCapGainParam {
    /** 参数键值 */
    private int key;
    /** 参数长度（固定为1字节） */
    private int len;
    /** 增益值（单位：dB） */
    private int gain;

    /** 默认构造函数 */
    public AntCapGainParam() {
        this.len = 1;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param gain 增益值（单位：dB）
     */
    public AntCapGainParam(int key, int gain) {
        this.key = key;
        this.gain = gain;
        this.len = 1;  // 固定长度
    }
}