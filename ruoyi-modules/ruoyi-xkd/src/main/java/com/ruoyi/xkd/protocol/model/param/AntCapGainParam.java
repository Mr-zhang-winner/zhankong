package com.ruoyi.xkd.protocol.model.param;

/**
 * 增益能力参数
 * 对应C语言的 ANT_CAP_GAIN_T
 * 用于存储天线设备的增益能力信息
 * 
 * 参数格式：key(1字节) + len(1字节) + gain(1字节)
 */
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

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getGain() {
        return gain;
    }

    public void setGain(int gain) {
        this.gain = gain;
    }
}