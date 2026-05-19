package com.ruoyi.xkd.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 设备状态对象 t_device_status
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
public class TDeviceStatus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 状态ID */
    private Long statusId;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long deviceId;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 方位角 */
    @Excel(name = "方位角")
    private BigDecimal azimuth;

    /** 俯仰角 */
    @Excel(name = "俯仰角")
    private BigDecimal elevation;

    /** 工作电压 */
    @Excel(name = "工作电压")
    private BigDecimal voltage;

    /** 工作参数JSON */
    @Excel(name = "工作参数JSON")
    private String workParams;

    /** 运行状态：NORMAL/ABNORMAL/OFFLINE */
    @Excel(name = "运行状态：NORMAL/ABNORMAL/OFFLINE")
    private String runStatus;

    /** 采集时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采集时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date collectTime;

    /** 上报时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上报时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportTime;

    public void setStatusId(Long statusId) 
    {
        this.statusId = statusId;
    }

    public Long getStatusId() 
    {
        return statusId;
    }

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

    public void setAzimuth(BigDecimal azimuth) 
    {
        this.azimuth = azimuth;
    }

    public BigDecimal getAzimuth() 
    {
        return azimuth;
    }

    public void setElevation(BigDecimal elevation) 
    {
        this.elevation = elevation;
    }

    public BigDecimal getElevation() 
    {
        return elevation;
    }

    public void setVoltage(BigDecimal voltage) 
    {
        this.voltage = voltage;
    }

    public BigDecimal getVoltage() 
    {
        return voltage;
    }

    public void setWorkParams(String workParams) 
    {
        this.workParams = workParams;
    }

    public String getWorkParams() 
    {
        return workParams;
    }

    public void setRunStatus(String runStatus) 
    {
        this.runStatus = runStatus;
    }

    public String getRunStatus() 
    {
        return runStatus;
    }

    public void setCollectTime(Date collectTime) 
    {
        this.collectTime = collectTime;
    }

    public Date getCollectTime() 
    {
        return collectTime;
    }

    public void setReportTime(Date reportTime) 
    {
        this.reportTime = reportTime;
    }

    public Date getReportTime() 
    {
        return reportTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("statusId", getStatusId())
            .append("deviceId", getDeviceId())
            .append("deviceCode", getDeviceCode())
            .append("deviceName", getDeviceName())
            .append("azimuth", getAzimuth())
            .append("elevation", getElevation())
            .append("voltage", getVoltage())
            .append("workParams", getWorkParams())
            .append("runStatus", getRunStatus())
            .append("collectTime", getCollectTime())
            .append("reportTime", getReportTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
