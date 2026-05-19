package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.U16Union;

/**
 * 告警参数
 * 对应C语言的 ANT_ALARM_T
 * 用于存储天线设备告警状态
 * 
 * 参数格式：key(1字节) + len(1字节) + ant_alarm(2字节)
 */
public class AntAlarmParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为2字节） */
    private int len;
    
    /** 告警状态（16位无符号整数，每一位代表一个告警类型） */
    private U16Union antAlarm;

    /** 默认构造函数 */
    public AntAlarmParam() {
        this.antAlarm = new U16Union();
        this.len = 2;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param alarm 告警状态
     */
    public AntAlarmParam(int key, int alarm) {
        this.key = key;
        this.antAlarm = new U16Union(alarm);
        this.len = 2;  // 固定长度
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

    public U16Union getAntAlarm() {
        return antAlarm;
    }

    public void setAntAlarm(U16Union antAlarm) {
        this.antAlarm = antAlarm;
    }
}