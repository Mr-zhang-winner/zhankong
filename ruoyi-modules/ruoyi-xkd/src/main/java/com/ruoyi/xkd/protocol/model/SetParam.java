package com.ruoyi.xkd.protocol.model;

import com.ruoyi.xkd.protocol.model.param.*;

/**
 * 设置参数集合
 * 对应C语言的 SET_PARAM
 * 包含天线设置命令（CMD_SET 0x80）的所有参数类型
 * 用于封装发送给天线设备的配置参数
 */
public class SetParam {
    /** 卫星切换参数 */
    private ChangeSatParam changeSatT;
    /** 发射频率参数 */
    private AntTxFreqParam antTxFreqT;
    /** 中频发射频率参数 */
    private AntMfTxFreqParam antMfTxFreqT;
    /** 接收频率参数 */
    private AntRxFreqParam antRxFreqT;
    /** 中频接收频率参数 */
    private AntMfRxFreqParam antMfRxFreqT;
    /** BUC衰减参数 */
    private AntBucDecayParam antBucDecayT;
    /** BDC衰减参数 */
    private AntBdcDecayParam antBdcDecayT;
    /** 发射极化参数 */
    private AntTxPolParam antTxPolT;
    /** 接收极化参数 */
    private AntRxPolParam antRxPolT;
    /** 开关状态参数 */
    private AntSwStateParam antSwStateT;

    /** 默认构造函数，初始化所有参数成员 */
    public SetParam() {
        this.changeSatT = new ChangeSatParam();
        this.antTxFreqT = new AntTxFreqParam();
        this.antMfTxFreqT = new AntMfTxFreqParam();
        this.antRxFreqT = new AntRxFreqParam();
        this.antMfRxFreqT = new AntMfRxFreqParam();
        this.antBucDecayT = new AntBucDecayParam();
        this.antBdcDecayT = new AntBdcDecayParam();
        this.antTxPolT = new AntTxPolParam();
        this.antRxPolT = new AntRxPolParam();
        this.antSwStateT = new AntSwStateParam();
    }

    public ChangeSatParam getChangeSatT() {
        return changeSatT;
    }

    public void setChangeSatT(ChangeSatParam changeSatT) {
        this.changeSatT = changeSatT;
    }

    public AntTxFreqParam getAntTxFreqT() {
        return antTxFreqT;
    }

    public void setAntTxFreqT(AntTxFreqParam antTxFreqT) {
        this.antTxFreqT = antTxFreqT;
    }

    public AntMfTxFreqParam getAntMfTxFreqT() {
        return antMfTxFreqT;
    }

    public void setAntMfTxFreqT(AntMfTxFreqParam antMfTxFreqT) {
        this.antMfTxFreqT = antMfTxFreqT;
    }

    public AntRxFreqParam getAntRxFreqT() {
        return antRxFreqT;
    }

    public void setAntRxFreqT(AntRxFreqParam antRxFreqT) {
        this.antRxFreqT = antRxFreqT;
    }

    public AntMfRxFreqParam getAntMfRxFreqT() {
        return antMfRxFreqT;
    }

    public void setAntMfRxFreqT(AntMfRxFreqParam antMfRxFreqT) {
        this.antMfRxFreqT = antMfRxFreqT;
    }

    public AntBucDecayParam getAntBucDecayT() {
        return antBucDecayT;
    }

    public void setAntBucDecayT(AntBucDecayParam antBucDecayT) {
        this.antBucDecayT = antBucDecayT;
    }

    public AntBdcDecayParam getAntBdcDecayT() {
        return antBdcDecayT;
    }

    public void setAntBdcDecayT(AntBdcDecayParam antBdcDecayT) {
        this.antBdcDecayT = antBdcDecayT;
    }

    public AntTxPolParam getAntTxPolT() {
        return antTxPolT;
    }

    public void setAntTxPolT(AntTxPolParam antTxPolT) {
        this.antTxPolT = antTxPolT;
    }

    public AntRxPolParam getAntRxPolT() {
        return antRxPolT;
    }

    public void setAntRxPolT(AntRxPolParam antRxPolT) {
        this.antRxPolT = antRxPolT;
    }

    public AntSwStateParam getAntSwStateT() {
        return antSwStateT;
    }

    public void setAntSwStateT(AntSwStateParam antSwStateT) {
        this.antSwStateT = antSwStateT;
    }
}