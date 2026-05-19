package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.BitSw;
import com.ruoyi.xkd.protocol.model.union.U8Union;

/**
 * 开关状态参数
 * 对应C语言的 ANT_SW_STATE_T
 * 用于存储天线开关状态
 * 
 * 参数格式：key(1字节) + len(1字节) + sw_state(1字节)
 * 
 * sw_state 为 U8_UNION 类型，可通过位域访问：
 * - sw_tx: 发射开关状态（bit[1:0]）
 * - sw_rx: 接收开关状态（bit[3:2]）
 * - sw_follow: 跟踪开关状态（bit[5:4]）
 * - reserve: 保留位（bit[7:6]）
 */
public class AntSwStateParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为1字节） */
    private int len;
    
    /** 开关状态（U8_UNION类型，支持位域操作） */
    private U8Union swState;

    /** 默认构造函数 */
    public AntSwStateParam() {
        this.swState = new U8Union();
        this.len = 1;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param state 开关状态
     */
    public AntSwStateParam(int key, int state) {
        this.key = key;
        this.swState = new U8Union(state);
        this.len = 1;  // 固定长度
    }

    /**
     * 使用 BitSw 位域初始化
     * @param key 参数键值
     * @param sw BitSw 位域对象
     */
    public AntSwStateParam(int key, BitSw sw) {
        this.key = key;
        this.swState = new U8Union(sw.getValue());
        this.len = 1;  // 固定长度
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

    public U8Union getSwState() {
        return swState;
    }

    public void setSwState(U8Union swState) {
        this.swState = swState;
    }

    /**
     * 获取 BitSw 位域对象，用于位域操作
     * @return BitSw 位域对象
     */
    public BitSw getBitSw() {
        return new BitSw(swState.getA());
    }

    /**
     * 通过 BitSw 位域对象设置开关状态
     * @param bitSw BitSw 位域对象
     */
    public void setBitSw(BitSw bitSw) {
        this.swState.setA(bitSw.getValue());
    }

    /**
     * 获取发射开关状态
     * @return 发射开关状态（0-3）
     */
    public int getSwTx() {
        return (swState.getA() >> 0) & 0x03;
    }

    /**
     * 设置发射开关状态
     * @param swTx 发射开关状态（0-3）
     */
    public void setSwTx(int swTx) {
        int value = swState.getA();
        value = (value & ~0x03) | (swTx & 0x03);
        swState.setA(value);
    }

    /**
     * 获取接收开关状态
     * @return 接收开关状态（0-3）
     */
    public int getSwRx() {
        return (swState.getA() >> 2) & 0x03;
    }

    /**
     * 设置接收开关状态
     * @param swRx 接收开关状态（0-3）
     */
    public void setSwRx(int swRx) {
        int value = swState.getA();
        value = (value & ~0x0C) | ((swRx & 0x03) << 2);
        swState.setA(value);
    }

    /**
     * 获取跟踪开关状态
     * @return 跟踪开关状态（0-3）
     */
    public int getSwFollow() {
        return (swState.getA() >> 4) & 0x03;
    }

    /**
     * 设置跟踪开关状态
     * @param swFollow 跟踪开关状态（0-3）
     */
    public void setSwFollow(int swFollow) {
        int value = swState.getA();
        value = (value & ~0x30) | ((swFollow & 0x03) << 4);
        swState.setA(value);
    }

    /**
     * 获取保留位值
     * @return 保留位值（0-3）
     */
    public int getReserve() {
        return (swState.getA() >> 6) & 0x03;
    }

    /**
     * 设置保留位值
     * @param reserve 保留位值（0-3）
     */
    public void setReserve(int reserve) {
        int value = swState.getA();
        value = (value & ~0xC0) | ((reserve & 0x03) << 6);
        swState.setA(value);
    }
}