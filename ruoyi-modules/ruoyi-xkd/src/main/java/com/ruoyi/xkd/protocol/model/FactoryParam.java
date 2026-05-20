package com.ruoyi.xkd.protocol.model;

import lombok.Data;

/**
 * 出厂参数结构体
 * 对应C语言的 FACTORY_PARAM
 * 用于存储天线设备的出厂信息
 */
@Data
public class FactoryParam {
    /** 设备名称（16字节） */
    private String strDeviceName;
    
    /** 设备制造商（24字节） */
    private String strDeviceOem;
    
    /** 设备序列号（16字节） */
    private String strDeviceSn;
    
    /** 生产日期（16字节，格式：YYYYMMDD） */
    private String strDeviceDate;
    
    /** 软件版本号（16字节） */
    private String strSoftVersion;
    
    /** 设备信息有效性标识（0=无效，1=有效） */
    private int deviceInfoValid;
}