package com.ruoyi.xkd.domain.dto;

import com.ruoyi.xkd.domain.TDeviceStatus;

import java.math.BigDecimal;
import java.util.Date;

public class DeviceStatusPushDTO {

    private Long statusId;

    private Long deviceId;

    private String deviceCode;

    private String deviceName;

    private BigDecimal azimuth;

    private BigDecimal elevation;

    private BigDecimal voltage;

    private String workParams;

    private String runStatus;

    private Date collectTime;

    private Date reportTime;

    public DeviceStatusPushDTO() {
    }

    public DeviceStatusPushDTO(TDeviceStatus deviceStatus) {
        this.statusId = deviceStatus.getStatusId();
        this.deviceId = deviceStatus.getDeviceId();
        this.deviceCode = deviceStatus.getDeviceCode();
        this.deviceName = deviceStatus.getDeviceName();
        this.azimuth = deviceStatus.getAzimuth();
        this.elevation = deviceStatus.getElevation();
        this.voltage = deviceStatus.getVoltage();
        this.workParams = deviceStatus.getWorkParams();
        this.runStatus = deviceStatus.getRunStatus();
        this.collectTime = deviceStatus.getCollectTime();
        this.reportTime = deviceStatus.getReportTime();
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public BigDecimal getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(BigDecimal azimuth) {
        this.azimuth = azimuth;
    }

    public BigDecimal getElevation() {
        return elevation;
    }

    public void setElevation(BigDecimal elevation) {
        this.elevation = elevation;
    }

    public BigDecimal getVoltage() {
        return voltage;
    }

    public void setVoltage(BigDecimal voltage) {
        this.voltage = voltage;
    }

    public String getWorkParams() {
        return workParams;
    }

    public void setWorkParams(String workParams) {
        this.workParams = workParams;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }
}
