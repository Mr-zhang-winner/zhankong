package com.ruoyi.xkd.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 设备配置对象 t_device_config
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Data
public class TDeviceConfig extends BaseEntity {
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