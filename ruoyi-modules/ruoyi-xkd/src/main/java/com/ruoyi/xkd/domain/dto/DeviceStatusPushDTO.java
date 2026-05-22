package com.ruoyi.xkd.domain.dto;

import com.ruoyi.xkd.domain.TDeviceStatus;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}