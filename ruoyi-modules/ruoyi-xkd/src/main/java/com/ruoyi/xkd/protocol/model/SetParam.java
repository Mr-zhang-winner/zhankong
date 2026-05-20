package com.ruoyi.xkd.protocol.model;

import com.ruoyi.xkd.protocol.model.param.*;
import lombok.Data;

/**
 * 设置参数集合
 * 对应C语言的 SET_PARAM
 * 包含天线设置命令（CMD_SET 0x80）的所有参数类型
 * 用于封装发送给天线设备的配置参数
 */
@Data
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
}