package com.ruoyi.xkd.protocol.model.param;

/**
 * 天线状态参数
 * 对应C语言的天线状态参数结构
 * 用于存储天线设备的运行状态
 * 
 * 参数格式：key(1字节) + len(1字节) + ant_status(1字节)
 * 
 * 状态定义：
 *   0x00 = 正常
 *   0x01 = 异常
 */
public class AntStatusParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为1字节） */
    private int len;
    
    /** 天线状态（8位无符号整数） */
    private int antStatus;

    /** 默认构造函数 */
    public AntStatusParam() {
        this.len = 1;
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param status 天线状态
     */
    public AntStatusParam(int key, int status) {
        this.key = key;
        this.antStatus = status;
        this.len = 1;
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

    public int getAntStatus() {
        return antStatus;
    }

    public void setAntStatus(int antStatus) {
        this.antStatus = antStatus;
    }
}