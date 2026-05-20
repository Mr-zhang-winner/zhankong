package com.ruoyi.xkd.protocol.model;

import lombok.Data;

/**
 * 惯导参数结构体
 * 对应C语言的 INS_PARAM
 * 用于存储惯性导航系统的姿态和角速度信息
 */
@Data
public class InsParam {
    /** 航向角（16位无符号整数，单位：0.01度） */
    private int heading;
    
    /** 俯仰角（16位有符号整数，单位：0.01度） */
    private int pitch;
    
    /** 横滚角（16位有符号整数，单位：0.01度） */
    private int roll;
    
    /** X轴角速度（32位有符号整数，单位：0.001度/秒） */
    private int wx;
    
    /** Y轴角速度（32位有符号整数，单位：0.001度/秒） */
    private int wy;
    
    /** Z轴角速度（32位有符号整数，单位：0.001度/秒） */
    private int wz;
}