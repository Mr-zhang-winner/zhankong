package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.U16Union;

/**
 * BUC衰减参数
 * 对应C语言的 ANT_BUC_DECAY_T
 * 用于存储BUC（上变频器）衰减值
 * 
 * 参数格式：key(1字节) + len(1字节) + ant_buc_decay(2字节)
 */
public class AntBucDecayParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为2字节） */
    private int len;
    
    /** BUC衰减值（16位无符号整数，单位：0.1dB） */
    private U16Union antBucDecay;

    /** 默认构造函数 */
    public AntBucDecayParam() {
        this.antBucDecay = new U16Union();
        this.len = 2;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param decay BUC衰减值（单位：0.1dB）
     */
    public AntBucDecayParam(int key, int decay) {
        this.key = key;
        this.antBucDecay = new U16Union(decay);
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

    public U16Union getAntBucDecay() {
        return antBucDecay;
    }

    public void setAntBucDecay(U16Union antBucDecay) {
        this.antBucDecay = antBucDecay;
    }
}