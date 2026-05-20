package com.ruoyi.xkd.protocol.model;

import lombok.Data;

/**
 * 控制器参数结构体
 * 对应C语言的 CONTROLLER_PARAM
 * 用于存储天线控制器的核心参数
 */
@Data
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
}