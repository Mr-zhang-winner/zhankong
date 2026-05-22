package com.ruoyi.xkd.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 站址配置对象 t_station_config
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TStationConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 站址ID */
    private Long stationId;

    /** 站址名称 */
    @Excel(name = "站址名称")
    private String stationName;

    /** 经度 */
    @Excel(name = "经度")
    private BigDecimal longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private BigDecimal latitude;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contact;

    /** 状态：0启用 1禁用 */
    @Excel(name = "状态：0启用 1禁用")
    private String status;
}