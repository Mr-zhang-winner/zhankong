package com.ruoyi.xkd.protocol.model.param;

import lombok.Data;

/**
 * 发射极化参数
 * 对应C语言的 ANT_TX_POL_T
 * 用于存储天线发射极化方式
 * 
 * 参数格式：key(1字节) + len(1字节) + ant_tx_pol(1字节)
 */
@Data
public class AntTxPolParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为1字节） */
    private int len;
    
    /** 发射极化方式（0=水平极化，1=垂直极化，其他=保留） */
    private int antTxPol;

    /** 默认构造函数 */
    public AntTxPolParam() {
        this.len = 1;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param pol 发射极化方式
     */
    public AntTxPolParam(int key, int pol) {
        this.key = key;
        this.antTxPol = pol;
        this.len = 1;  // 固定长度
    }
}