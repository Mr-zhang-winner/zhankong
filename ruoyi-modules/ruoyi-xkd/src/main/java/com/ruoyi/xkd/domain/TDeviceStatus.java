package com.ruoyi.xkd.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备状态对象 t_device_status
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
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

    /** 电压 */
    @Excel(name = "电压")
    private BigDecimal voltage;

    /** 工作参数JSON */
    @Excel(name = "工作参数JSON")
    private String workParams;

    /** 运行状态 */
    @Excel(name = "运行状态")
    private String runStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date collectTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportTime;
}