package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.U16Union;

/**
 * 能力参数
 * 对应C语言的 ANT_CAP_T
 * 用于存储天线设备的能力信息
 * 
 * 参数格式：key(1字节) + len(1字节) + eirp(1字节) + min_eirp(1字节) + ant_gt(2字节) + min_gt(2字节) + ue_pitch(1字节)
 */
public class AntCapParam {
    /** 参数键值 */
    private int key;
    /** 参数长度（固定为8字节） */
    private int len;
    /** 有效全向辐射功率（单位：dBW） */
    private int eirp;
    /** 最小有效全向辐射功率（单位：dBW） */
    private int minEirp;
    /** 天线增益与噪声温度比（16位无符号整数，单位：0.1dB/K） */
    private U16Union antGt;
    /** 最小天线增益与噪声温度比（16位无符号整数，单位：0.1dB/K） */
    private U16Union minGt;
    /** 用户设备俯仰角 */
    private int uePitch;

    /** 默认构造函数 */
    public AntCapParam() {
        this.antGt = new U16Union();
        this.minGt = new U16Union();
        this.len = 8;  // 固定长度
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

    public int getEirp() {
        return eirp;
    }

    public void setEirp(int eirp) {
        this.eirp = eirp;
    }

    public int getMinEirp() {
        return minEirp;
    }

    public void setMinEirp(int minEirp) {
        this.minEirp = minEirp;
    }

    public U16Union getAntGt() {
        return antGt;
    }

    public void setAntGt(U16Union antGt) {
        this.antGt = antGt;
    }

    public U16Union getMinGt() {
        return minGt;
    }

    public void setMinGt(U16Union minGt) {
        this.minGt = minGt;
    }

    public int getUePitch() {
        return uePitch;
    }

    public void setUePitch(int uePitch) {
        this.uePitch = uePitch;
    }
}