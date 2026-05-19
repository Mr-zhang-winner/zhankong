package com.ruoyi.xkd.protocol.model.param;

import com.ruoyi.xkd.protocol.model.union.S16Union;
import lombok.Data;

/**
 * 信号强度参数
 * 对应C语言的 ANT_PSRP_T
 * 用于存储天线接收信号强度
 * 
 * 参数格式：key(1字节) + len(1字节) + ant_psrp(2字节)
 */
@Data
public class AntPsrpParam {
    /** 参数键值 */
    private int key;
    
    /** 参数长度（固定为2字节） */
    private int len;
    
    /** 信号强度（16位有符号整数，单位：dBm） */
    private S16Union antPsrp;

    /** 默认构造函数 */
    public AntPsrpParam() {
        this.antPsrp = new S16Union();
        this.len = 2;  // 固定长度
    }

    /**
     * 带初始值的构造函数
     * @param key 参数键值
     * @param psrp 信号强度（单位：dBm）
     */
    public AntPsrpParam(int key, int psrp) {
        this.key = key;
        this.antPsrp = new S16Union(psrp);
        this.len = 2;  // 固定长度
    }
}