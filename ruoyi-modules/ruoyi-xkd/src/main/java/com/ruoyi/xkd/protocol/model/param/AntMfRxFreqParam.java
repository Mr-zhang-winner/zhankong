package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.U32Union;
import lombok.Data;

/**
 * 中频接收频率参数
 * 对应C语言的 ANT_MFRX_FREQ_T
 * 用于存储天线中频接收频率
 * 
 * 参数格式：key(1字节) + len(1字节) + ant_mfrx_freq(4字节)
 */
@Data
public class AntMfRxFreqParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为4字节） */
    private int len;
    
    /** 中频接收频率（32位无符号整数，单位：Hz） */
    private U32Union antMfRxFreq;

    /** 默认构造函数 */
    public AntMfRxFreqParam() {
        this.antMfRxFreq = new U32Union();
        this.len = 4;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param freq 中频接收频率（单位：Hz）
     */
    public AntMfRxFreqParam(int key, long freq) {
        this.key = key;
        this.antMfRxFreq = new U32Union(freq);
        this.len = 4;  // 固定长度
    }
}