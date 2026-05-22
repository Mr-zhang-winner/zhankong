package com.ruoyi.xkd.protocol.model;

/**
 * 惯导参数结构体
 * 对应C语言的 INS_PARAM
 * 用于存储惯性导航系统的姿态和角速度信息
 */
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

    /** 默认构造函数 */
    public InsParam() {}

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getWx() {
        return wx;
    }

    public void setWx(int wx) {
        this.wx = wx;
    }

    public int getWy() {
        return wy;
    }

    public void setWy(int wy) {
        this.wy = wy;
    }

    public int getWz() {
        return wz;
    }

    public void setWz(int wz) {
        this.wz = wz;
    }
}