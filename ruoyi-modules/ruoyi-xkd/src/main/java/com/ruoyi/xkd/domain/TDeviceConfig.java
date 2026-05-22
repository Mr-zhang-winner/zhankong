package com.ruoyi.xkd.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备配置对象 t_device_config
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TDeviceConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long deviceId;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String deviceType;

    /** 所属站址ID */
    @Excel(name = "所属站址ID")
    private Long stationId;

    /** 设备IP */
    @Excel(name = "设备IP")
    private String ipAddress;

    /** UDP端口 */
    @Excel(name = "UDP端口")
    private Long udpPort;

    /** 设备配置参数JSON */
    @Excel(name = "设备配置参数JSON")
    private String configParams;

    /** 状态：0启用 1禁用 */
    @Excel(name = "状态：0启用 1禁用")
    private String status;

    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }

    public void setDeviceCode(String deviceCode) 
    {
        this.deviceCode = deviceCode;
    }

    public String getDeviceCode() 
    {
        return deviceCode;
    }

    public void setDeviceName(String deviceName) 
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName() 
    {
        return deviceName;
    }

    public void setDeviceType(String deviceType) 
    {
        this.deviceType = deviceType;
    }

    public String getDeviceType() 
    {
        return deviceType;
    }

    public void setStationId(Long stationId) 
    {
        this.stationId = stationId;
    }

    public Long getStationId() 
    {
        return stationId;
    }

    public void setIpAddress(String ipAddress) 
    {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() 
    {
        return ipAddress;
    }

    public void setUdpPort(Long udpPort) 
    {
        this.udpPort = udpPort;
    }

    public Long getUdpPort() 
    {
        return udpPort;
    }

    public void setConfigParams(String configParams) 
    {
        this.configParams = configParams;
    }

    public String getConfigParams() 
    {
        return configParams;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deviceId", getDeviceId())
            .append("deviceCode", getDeviceCode())
            .append("deviceName", getDeviceName())
            .append("deviceType", getDeviceType())
            .append("stationId", getStationId())
            .append("ipAddress", getIpAddress())
            .append("udpPort", getUdpPort())
            .append("configParams", getConfigParams())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
