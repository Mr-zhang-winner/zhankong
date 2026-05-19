package com.ruoyi.xkd.protocol.model;

/**
 * 出厂参数结构体
 * 对应C语言的 FACTORY_PARAM
 * 用于存储天线设备的出厂信息
 */
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

    /** 默认构造函数 */
    public FactoryParam() {}

    /**
     * 获取设备名称
     * @return 设备名称
     */
    public String getStrDeviceName() {
        return strDeviceName;
    }

    /**
     * 设置设备名称
     * @param strDeviceName 设备名称
     */
    public void setStrDeviceName(String strDeviceName) {
        this.strDeviceName = strDeviceName;
    }

    /**
     * 获取设备制造商
     * @return 设备制造商
     */
    public String getStrDeviceOem() {
        return strDeviceOem;
    }

    /**
     * 设置设备制造商
     * @param strDeviceOem 设备制造商
     */
    public void setStrDeviceOem(String strDeviceOem) {
        this.strDeviceOem = strDeviceOem;
    }

    /**
     * 获取设备序列号
     * @return 设备序列号
     */
    public String getStrDeviceSn() {
        return strDeviceSn;
    }

    /**
     * 设置设备序列号
     * @param strDeviceSn 设备序列号
     */
    public void setStrDeviceSn(String strDeviceSn) {
        this.strDeviceSn = strDeviceSn;
    }

    /**
     * 获取生产日期
     * @return 生产日期
     */
    public String getStrDeviceDate() {
        return strDeviceDate;
    }

    /**
     * 设置生产日期
     * @param strDeviceDate 生产日期
     */
    public void setStrDeviceDate(String strDeviceDate) {
        this.strDeviceDate = strDeviceDate;
    }

    /**
     * 获取软件版本号
     * @return 软件版本号
     */
    public String getStrSoftVersion() {
        return strSoftVersion;
    }

    /**
     * 设置软件版本号
     * @param strSoftVersion 软件版本号
     */
    public void setStrSoftVersion(String strSoftVersion) {
        this.strSoftVersion = strSoftVersion;
    }

    /**
     * 获取设备信息有效性标识
     * @return 有效性标识（0=无效，1=有效）
     */
    public int getDeviceInfoValid() {
        return deviceInfoValid;
    }

    /**
     * 设置设备信息有效性标识
     * @param deviceInfoValid 有效性标识（0=无效，1=有效）
     */
    public void setDeviceInfoValid(int deviceInfoValid) {
        this.deviceInfoValid = deviceInfoValid;
    }
}