package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.U8Union;
import lombok.Data;

/**
 * 天线模式参数
 * 对应C语言的 ANT_MODE_T
 * 用于存储天线工作模式
 * 
 * 参数格式：key(1字节) + len(1字节) + ant_mode(1字节)
 */
@Data
public class AntModeParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为1字节） */
    private int len;
    
    /** 天线模式（U8_UNION类型） */
    private U8Union antMode;

    /** 默认构造函数 */
    public AntModeParam() {
        this.antMode = new U8Union();
        this.len = 1;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param antMode 天线模式
     */
    public AntModeParam(int key, int antMode) {
        this.key = key;
        this.antMode = new U8Union(antMode);
        this.len = 1;  // 固定长度
    }

    /**
     * 获取天线模式整数值
     * @return 天线模式值
     */
    public int getAntModeValue() {
        return antMode.getA();
    }

    /**
     * 设置天线模式整数值
     * @param antMode 天线模式值
     */
    public void setAntModeValue(int antMode) {
        this.antMode.setA(antMode);
    }
}