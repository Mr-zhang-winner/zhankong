package com.ruoyi.xkd.protocol.model;

/**
 * 控制器参数结构体
 * 对应C语言的 CONTROLLER_PARAM
 * 用于存储天线控制器的核心参数
 */
public class ControllerParam {
    /** 卫星ID（16位无符号整数） */
    private int satId;
    
    /** 参数有效性标识（0=无效，1=有效） */
    private int valid;
    
    /** GPS周数（32位无符号整数） */
    private long gpsWeek;
    
    /** GPS秒数（32位无符号整数） */
    private long gpsSecond;
    
    /** GPS微秒数（16位无符号整数） */
    private int gpsMicsecond;
    
    /** 发射频率（32位无符号整数，单位：Hz） */
    private long sendFreq;
    
    /** 发射带宽频率（32位无符号整数，单位：Hz） */
    private long sendBwpFreq;
    
    /** 接收频率（32位无符号整数，单位：Hz） */
    private long recvFreq;
    
    /** 接收带宽频率（32位无符号整数，单位：Hz） */
    private long recvBwpFreq;
    
    /** BUC衰减值（16位无符号整数，单位：0.1dB） */
    private int bucDecay;
    
    /** BDC衰减值（16位无符号整数，单位：0.1dB） */
    private int bdcDecay;
    
    /** 天线开关状态 */
    private int antSwitch;
    
    /** 极化配置 */
    private int antPolConfig;
    
    /** 天线状态 */
    private int antStatus;
    
    /** 天线类型 */
    private int antType;
    
    /** 天线接收信号强度（16位有符号整数） */
    private int antRsrp;
    
    /** 天线角度（32位有符号整数） */
    private int antAngle;

    /** 默认构造函数 */
    public ControllerParam() {}

    public int getSatId() {
        return satId;
    }

    public void setSatId(int satId) {
        this.satId = satId;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public long getGpsWeek() {
        return gpsWeek;
    }

    public void setGpsWeek(long gpsWeek) {
        this.gpsWeek = gpsWeek;
    }

    public long getGpsSecond() {
        return gpsSecond;
    }

    public void setGpsSecond(long gpsSecond) {
        this.gpsSecond = gpsSecond;
    }

    public int getGpsMicsecond() {
        return gpsMicsecond;
    }

    public void setGpsMicsecond(int gpsMicsecond) {
        this.gpsMicsecond = gpsMicsecond;
    }

    public long getSendFreq() {
        return sendFreq;
    }

    public void setSendFreq(long sendFreq) {
        this.sendFreq = sendFreq;
    }

    public long getSendBwpFreq() {
        return sendBwpFreq;
    }

    public void setSendBwpFreq(long sendBwpFreq) {
        this.sendBwpFreq = sendBwpFreq;
    }

    public long getRecvFreq() {
        return recvFreq;
    }

    public void setRecvFreq(long recvFreq) {
        this.recvFreq = recvFreq;
    }

    public long getRecvBwpFreq() {
        return recvBwpFreq;
    }

    public void setRecvBwpFreq(long recvBwpFreq) {
        this.recvBwpFreq = recvBwpFreq;
    }

    public int getBucDecay() {
        return bucDecay;
    }

    public void setBucDecay(int bucDecay) {
        this.bucDecay = bucDecay;
    }

    public int getBdcDecay() {
        return bdcDecay;
    }

    public void setBdcDecay(int bdcDecay) {
        this.bdcDecay = bdcDecay;
    }

    public int getAntSwitch() {
        return antSwitch;
    }

    public void setAntSwitch(int antSwitch) {
        this.antSwitch = antSwitch;
    }

    public int getAntPolConfig() {
        return antPolConfig;
    }

    public void setAntPolConfig(int antPolConfig) {
        this.antPolConfig = antPolConfig;
    }

    public int getAntStatus() {
        return antStatus;
    }

    public void setAntStatus(int antStatus) {
        this.antStatus = antStatus;
    }

    public int getAntType() {
        return antType;
    }

    public void setAntType(int antType) {
        this.antType = antType;
    }

    public int getAntRsrp() {
        return antRsrp;
    }

    public void setAntRsrp(int antRsrp) {
        this.antRsrp = antRsrp;
    }

    public int getAntAngle() {
        return antAngle;
    }

    public void setAntAngle(int antAngle) {
        this.antAngle = antAngle;
    }
}