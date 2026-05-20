package com.ruoyi.xkd.protocol.model;

import com.ruoyi.xkd.protocol.model.union.S32Union;
import com.ruoyi.xkd.protocol.model.union.U16Union;
import com.ruoyi.xkd.protocol.model.union.U32Union;
import lombok.Data;

/**
 * GPS时间参数结构体
 * 对应C语言的 GPSTOD_PARAM
 * 用于存储GPS时间和位置信息
 */
@Data
public class GpsTodParam {
    /** 年份（16位无符号整数） */
    private U16Union year;
    
    /** 月份（1-12） */
    private int month;
    
    /** 日期（1-31） */
    private int day;
    
    /** UTC秒数（32位无符号整数） */
    private U32Union utcSecond;
    
    /** 经度（32位有符号整数，单位：0.00001度） */
    private S32Union longitude;
    
    /** 纬度（32位有符号整数，单位：0.00001度） */
    private S32Union latitude;
    
    /** 高度（32位有符号整数，单位：毫米） */
    private S32Union altitude;
    
    /** 东向速度（32位有符号整数，单位：毫米/秒） */
    private S32Union ve;
    
    /** 北向速度（32位有符号整数，单位：毫米/秒） */
    private S32Union vn;
    
    /** 天向速度（32位有符号整数，单位：毫米/秒） */
    private S32Union vu;

    /** 默认构造函数，初始化所有联合体成员 */
    public GpsTodParam() {
        this.year = new U16Union();
        this.utcSecond = new U32Union();
        this.longitude = new S32Union();
        this.latitude = new S32Union();
        this.altitude = new S32Union();
        this.ve = new S32Union();
        this.vn = new S32Union();
        this.vu = new S32Union();
    }
}