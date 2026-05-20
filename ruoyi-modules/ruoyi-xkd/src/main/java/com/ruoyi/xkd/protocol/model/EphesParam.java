package com.ruoyi.xkd.protocol.model;

import com.ruoyi.xkd.protocol.model.union.DoubleUnion;
import com.ruoyi.xkd.protocol.model.union.U16Union;
import com.ruoyi.xkd.protocol.model.union.U32Union;
import com.ruoyi.xkd.protocol.model.union.U8Union;
import lombok.Data;

/**
 * 星历参数结构体
 * 对应C语言的 EPHES_PARAM
 * 用于存储卫星星历数据，支持最多20个历元点
 */
@Data
public class EphesParam {
    /** 最大历元点数量 */
    private static final int MAX_POINTS = 20;

    /** 卫星ID（16位无符号整数） */
    private U16Union satId;
    
    /** 数据点数（16位无符号整数） */
    private U16Union dataNum;
    
    /** 当前包号/总包号（8位无符号整数） */
    private U8Union curTotalNum;
    
    /** GPS周数数组（最多20个） */
    private U32Union[] gpsWeek;
    
    /** GPS秒数数组（最多20个） */
    private U32Union[] gpsSecond;
    
    /** WGS84 X坐标数组（最多20个，单位：米） */
    private DoubleUnion[] wgs84X;
    
    /** WGS84 Y坐标数组（最多20个，单位：米） */
    private DoubleUnion[] wgs84Y;
    
    /** WGS84 Z坐标数组（最多20个，单位：米） */
    private DoubleUnion[] wgs84Z;
    
    /** WGS84 X速度（单位：米/秒） */
    private DoubleUnion wgs84Vx;
    
    /** WGS84 Y速度（单位：米/秒） */
    private DoubleUnion wgs84Vy;
    
    /** WGS84 Z速度（单位：米/秒） */
    private DoubleUnion wgs84Vz;
    
    /** 数据序列号（32位无符号整数） */
    private U32Union dataSeq;
    
    /** 周偏移获取成功标识（0=失败，1=成功） */
    private int getWeekOffsetOk;
    
    /** 周偏移时间（16位无符号整数） */
    private int weekOffsetT;

    /** 默认构造函数，初始化所有联合体成员 */
    public EphesParam() {
        this.satId = new U16Union();
        this.dataNum = new U16Union();
        this.curTotalNum = new U8Union();
        this.gpsWeek = new U32Union[MAX_POINTS];
        this.gpsSecond = new U32Union[MAX_POINTS];
        this.wgs84X = new DoubleUnion[MAX_POINTS];
        this.wgs84Y = new DoubleUnion[MAX_POINTS];
        this.wgs84Z = new DoubleUnion[MAX_POINTS];
        this.wgs84Vx = new DoubleUnion();
        this.wgs84Vy = new DoubleUnion();
        this.wgs84Vz = new DoubleUnion();
        this.dataSeq = new U32Union();

        // 初始化数组元素
        for (int i = 0; i < MAX_POINTS; i++) {
            this.gpsWeek[i] = new U32Union();
            this.gpsSecond[i] = new U32Union();
            this.wgs84X[i] = new DoubleUnion();
            this.wgs84Y[i] = new DoubleUnion();
            this.wgs84Z[i] = new DoubleUnion();
        }
    }
}