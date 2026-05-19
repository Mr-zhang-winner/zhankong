package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.U16Union;
import com.ruoyi.xkd.protocol.model.union.U32Union;
import lombok.Data;

/**
 * 卫星切换参数
 * 对应C语言的 CHANGE_SAT_T
 * 用于存储卫星切换相关的配置信息
 * 
 * 参数格式：key(1字节) + len(1字节) + sat_id(2字节) + is_valid(1字节) + start_change(1字节) + gps_week(4字节) + gps_second(4字节) + gps_msecond(2字节)
 */
@Data
public class ChangeSatParam {
    /** 参数键值 */
    private int key;
    /** 参数长度（固定为14字节） */
    private int len;
    /** 卫星ID（16位无符号整数） */
    private U16Union satId;
    /** 卫星有效性标识（0=无效，1=有效） */
    private int isValid;
    /** 开始切换标识 */
    private int startChange;
    /** GPS周数（32位无符号整数） */
    private U32Union gpsWeek;
    /** GPS秒数（32位无符号整数） */
    private U32Union gpsSecond;
    /** GPS毫秒数（16位无符号整数） */
    private U16Union gpsMsecond;

    /** 默认构造函数 */
    public ChangeSatParam() {
        this.satId = new U16Union();
        this.gpsWeek = new U32Union();
        this.gpsSecond = new U32Union();
        this.gpsMsecond = new U16Union();
        this.len = 14;  // 固定长度
    }
}