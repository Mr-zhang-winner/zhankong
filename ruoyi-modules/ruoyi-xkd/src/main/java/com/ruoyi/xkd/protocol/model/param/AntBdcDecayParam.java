package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.U16Union;

/**
 * BDC衰减参数
 * 对应C语言的 ANT_BDC_DECAY_T
 * 用于存储BDC（下变频器）衰减值
 * 
 * 参数格式：key(1字节) + len(1字节) + ant_bdc_decay(2字节)
 */
public class AntBdcDecayParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为2字节） */
    private int len;
    
    /** BDC衰减值（16位无符号整数，单位：0.1dB） */
    private U16Union antBdcDecay;

    /** 默认构造函数 */
    public AntBdcDecayParam() {
        this.antBdcDecay = new U16Union();
        this.len = 2;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param decay BDC衰减值（单位：0.1dB）
     */
    public AntBdcDecayParam(int key, int decay) {
        this.key = key;
        this.antBdcDecay = new U16Union(decay);
        this.len = 2;  // 固定长度
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

    public U16Union getAntBdcDecay() {
        return antBdcDecay;
    }

    public void setAntBdcDecay(U16Union antBdcDecay) {
        this.antBdcDecay = antBdcDecay;
    }
}