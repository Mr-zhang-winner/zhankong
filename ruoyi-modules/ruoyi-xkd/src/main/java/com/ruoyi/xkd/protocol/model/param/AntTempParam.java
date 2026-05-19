package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.S16Union;
import lombok.Data;

/**
 * 温度参数
 * 对应C语言的 ANT_TEMP_T
 * 用于存储天线设备温度
 * 
 * 参数格式：key(1字节) + len(1字节) + ant_temp(2字节)
 */
@Data
public class AntTempParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为2字节） */
    private int len;
    
    /** 温度值（16位有符号整数，单位：0.1度） */
    private S16Union antTemp;

    /** 默认构造函数 */
    public AntTempParam() {
        this.antTemp = new S16Union();
        this.len = 2;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param temp 温度值（单位：0.1度）
     */
    public AntTempParam(int key, int temp) {
        this.key = key;
        this.antTemp = new S16Union(temp);
        this.len = 2;  // 固定长度
    }
}