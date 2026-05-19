package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.U16Union;
import com.ruoyi.xkd.protocol.model.union.U32Union;

/**
 * 卫星切换参数
 * 对应C语言的 CHANGE_SAT_T
 * 用于存储卫星切换相关的配置信息
 * 
 * 参数格式：key(1字节) + len(1字节) + sat_id(2字节) + is_valid(1字节) + start_change(1字节) + gps_week(4字节) + gps_second(4字节) + gps_msecond(2字节)
 */
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

    public U16Union getSatId() {
        return satId;
    }

    public void setSatId(U16Union satId) {
        this.satId = satId;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public int getStartChange() {
        return startChange;
    }

    public void setStartChange(int startChange) {
        this.startChange = startChange;
    }

    public U32Union getGpsWeek() {
        return gpsWeek;
    }

    public void setGpsWeek(U32Union gpsWeek) {
        this.gpsWeek = gpsWeek;
    }

    public U32Union getGpsSecond() {
        return gpsSecond;
    }

    public void setGpsSecond(U32Union gpsSecond) {
        this.gpsSecond = gpsSecond;
    }

    public U16Union getGpsMsecond() {
        return gpsMsecond;
    }

    public void setGpsMsecond(U16Union gpsMsecond) {
        this.gpsMsecond = gpsMsecond;
    }
}