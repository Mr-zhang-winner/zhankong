package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.U32Union;

/**
 * 中频发射频率参数
 * 对应C语言的 ANT_MFTX_FREQ_T
 * 用于存储天线中频发射频率
 * 
 * 参数格式：key(1字节) + len(1字节) + ant_mftx_freq(4字节)
 */
public class AntMfTxFreqParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为4字节） */
    private int len;
    
    /** 中频发射频率（32位无符号整数，单位：Hz） */
    private U32Union antMfTxFreq;

    /** 默认构造函数 */
    public AntMfTxFreqParam() {
        this.antMfTxFreq = new U32Union();
        this.len = 4;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param freq 中频发射频率（单位：Hz）
     */
    public AntMfTxFreqParam(int key, long freq) {
        this.key = key;
        this.antMfTxFreq = new U32Union(freq);
        this.len = 4;  // 固定长度
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

    public U32Union getAntMfTxFreq() {
        return antMfTxFreq;
    }

    public void setAntMfTxFreq(U32Union antMfTxFreq) {
        this.antMfTxFreq = antMfTxFreq;
    }
}