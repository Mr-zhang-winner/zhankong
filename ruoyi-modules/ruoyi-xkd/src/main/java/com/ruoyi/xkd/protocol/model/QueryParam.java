package com.ruoyi.xkd.protocol.model;

import com.ruoyi.xkd.protocol.model.param.*;

/**
 * 查询参数集合
 * 对应C语言的 QUERY_PARAM
 * 包含天线查询命令（CMD_QUERY 0x82）的所有参数类型
 * 用于存储从天线设备查询到的完整状态信息
 */
public class QueryParam {
    /** 天线模式参数 */
    private AntModeParam antModeT;
    /** 发射频率参数 */
    private AntTxFreqParam antTxFreqT;
    /** 接收频率参数 */
    private AntRxFreqParam antRxFreqT;
    /** 发射极化参数 */
    private AntTxPolParam antTxPolT;
    /** 接收极化参数 */
    private AntRxPolParam antRxPolT;
    /** BUC衰减参数 */
    private AntBucDecayParam antBucDecayT;
    /** BDC衰减参数 */
    private AntBdcDecayParam antBdcDecayT;
    /** 卫星ID参数 */
    private AntSatIdParam antSatIdT;
    /** 温度参数 */
    private AntTempParam antTempT;
    /** 天线状态参数 */
    private AntStatusParam antStatusT;
    /** 告警参数 */
    private AntAlarmParam antAlarmT;
    /** 姿态参数（方位角+俯仰角） */
    private AntAttitudeParam antAttitudeT;
    /** 信号强度参数 */
    private AntPsrpParam antPsrpT;
    /** 能力参数 */
    private AntCapParam antCapT;
    /** 发射增益能力参数 */
    private AntCapGainParam antCapSendGainT;
    /** BUC增益范围参数 */
    private AntCapGainParam bucGainRangeT;
    /** 接收增益能力参数 */
    private AntCapGainParam antCapRecvGainT;
    /** BDC增益范围参数 */
    private AntCapGainParam bdcGainRangeT;

    /** 默认构造函数，初始化所有参数成员 */
    public QueryParam() {
        this.antModeT = new AntModeParam();
        this.antTxFreqT = new AntTxFreqParam();
        this.antRxFreqT = new AntRxFreqParam();
        this.antTxPolT = new AntTxPolParam();
        this.antRxPolT = new AntRxPolParam();
        this.antBucDecayT = new AntBucDecayParam();
        this.antBdcDecayT = new AntBdcDecayParam();
        this.antSatIdT = new AntSatIdParam();
        this.antTempT = new AntTempParam();
        this.antStatusT = new AntStatusParam();
        this.antAlarmT = new AntAlarmParam();
        this.antAttitudeT = new AntAttitudeParam();
        this.antPsrpT = new AntPsrpParam();
        this.antCapT = new AntCapParam();
        this.antCapSendGainT = new AntCapGainParam();
        this.bucGainRangeT = new AntCapGainParam();
        this.antCapRecvGainT = new AntCapGainParam();
        this.bdcGainRangeT = new AntCapGainParam();
    }

    public AntModeParam getAntModeT() {
        return antModeT;
    }

    public void setAntModeT(AntModeParam antModeT) {
        this.antModeT = antModeT;
    }

    public AntTxFreqParam getAntTxFreqT() {
        return antTxFreqT;
    }

    public void setAntTxFreqT(AntTxFreqParam antTxFreqT) {
        this.antTxFreqT = antTxFreqT;
    }

    public AntRxFreqParam getAntRxFreqT() {
        return antRxFreqT;
    }

    public void setAntRxFreqT(AntRxFreqParam antRxFreqT) {
        this.antRxFreqT = antRxFreqT;
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

    public AntSatIdParam getAntSatIdT() {
        return antSatIdT;
    }

    public void setAntSatIdT(AntSatIdParam antSatIdT) {
        this.antSatIdT = antSatIdT;
    }

    public AntTempParam getAntTempT() {
        return antTempT;
    }

    public void setAntTempT(AntTempParam antTempT) {
        this.antTempT = antTempT;
    }

    public AntStatusParam getAntStatusT() {
        return antStatusT;
    }

    public void setAntStatusT(AntStatusParam antStatusT) {
        this.antStatusT = antStatusT;
    }

    public AntAlarmParam getAntAlarmT() {
        return antAlarmT;
    }

    public void setAntAlarmT(AntAlarmParam antAlarmT) {
        this.antAlarmT = antAlarmT;
    }

    public AntAttitudeParam getAntAttitudeT() {
        return antAttitudeT;
    }

    public void setAntAttitudeT(AntAttitudeParam antAttitudeT) {
        this.antAttitudeT = antAttitudeT;
    }

    public AntPsrpParam getAntPsrpT() {
        return antPsrpT;
    }

    public void setAntPsrpT(AntPsrpParam antPsrpT) {
        this.antPsrpT = antPsrpT;
    }

    public AntCapParam getAntCapT() {
        return antCapT;
    }

    public void setAntCapT(AntCapParam antCapT) {
        this.antCapT = antCapT;
    }

    public AntCapGainParam getAntCapSendGainT() {
        return antCapSendGainT;
    }

    public void setAntCapSendGainT(AntCapGainParam antCapSendGainT) {
        this.antCapSendGainT = antCapSendGainT;
    }

    public AntCapGainParam getBucGainRangeT() {
        return bucGainRangeT;
    }

    public void setBucGainRangeT(AntCapGainParam bucGainRangeT) {
        this.bucGainRangeT = bucGainRangeT;
    }

    public AntCapGainParam getAntCapRecvGainT() {
        return antCapRecvGainT;
    }

    public void setAntCapRecvGainT(AntCapGainParam antCapRecvGainT) {
        this.antCapRecvGainT = antCapRecvGainT;
    }

    public AntCapGainParam getBdcGainRangeT() {
        return bdcGainRangeT;
    }

    public void setBdcGainRangeT(AntCapGainParam bdcGainRangeT) {
        this.bdcGainRangeT = bdcGainRangeT;
    }
}