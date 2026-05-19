package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.U16Union;

/**
 * 卫星ID参数
 * 对应C语言的 ANT_SAT_ID_T
 * 用于存储当前锁定的卫星ID
 * 
 * 参数格式：key(1字节) + len(1字节) + sat_id(2字节)
 */
public class AntSatIdParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为2字节） */
    private int len;
    
    /** 卫星ID（16位无符号整数） */
    private U16Union satId;

    /** 默认构造函数 */
    public AntSatIdParam() {
        this.satId = new U16Union();
        this.len = 2;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param id 卫星ID
     */
    public AntSatIdParam(int key, int id) {
        this.key = key;
        this.satId = new U16Union(id);
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

    public U16Union getSatId() {
        return satId;
    }

    public void setSatId(U16Union satId) {
        this.satId = satId;
    }
}