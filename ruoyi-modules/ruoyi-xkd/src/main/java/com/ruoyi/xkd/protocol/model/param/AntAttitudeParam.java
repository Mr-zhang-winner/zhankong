package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.S16Union;
import com.ruoyi.xkd.protocol.model.union.U16Union;
import lombok.Data;

/**
 * 姿态参数
 * 对应C语言的 ANT_ATTITUDE_T
 * 用于存储天线方位角和俯仰角
 * 
 * 参数格式：key(1字节) + len(1字节) + ant_azimuth(2字节) + ant_pitch(2字节)
 */
@Data
public class AntAttitudeParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为4字节） */
    private int len;
    
    /** 方位角（16位无符号整数，单位：0.1度） */
    private U16Union antAzimuth;
    
    /** 俯仰角（16位有符号整数，单位：0.1度） */
    private S16Union antPitch;

    /** 默认构造函数 */
    public AntAttitudeParam() {
        this.antAzimuth = new U16Union();
        this.antPitch = new S16Union();
        this.len = 4;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param azimuth 方位角（单位：0.1度）
     * @param pitch 俯仰角（单位：0.1度）
     */
    public AntAttitudeParam(int key, int azimuth, int pitch) {
        this.key = key;
        this.antAzimuth = new U16Union(azimuth);
        this.antPitch = new S16Union(pitch);
        this.len = 4;  // 固定长度
    }
}