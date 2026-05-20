package com.ruoyi.xkd.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 卫星配置对象 t_satellite_config
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Data
public class TSatelliteConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 卫星ID */
    private Long satelliteId;

    /** 卫星编号 */
    @Excel(name = "卫星编号")
    private String satelliteCode;

    /** 卫星名称 */
    @Excel(name = "卫星名称")
    private String satelliteName;

    /** 星历类型：TLE/PVT/PARAM12 */
    @Excel(name = "星历类型：TLE/PVT/PARAM12")
    private String ephemerisType;

    /** 轨道参数JSON */
    @Excel(name = "轨道参数JSON")
    private String orbitParams;

    /** 接收参数JSON */
    @Excel(name = "接收参数JSON")
    private String receiveParams;

    /** 状态：0启用 1禁用 */
    @Excel(name = "状态：0启用 1禁用")
    private String status;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("satelliteId", getSatelliteId())
            .append("satelliteCode", getSatelliteCode())
            .append("satelliteName", getSatelliteName())
            .append("ephemerisType", getEphemerisType())
            .append("orbitParams", getOrbitParams())
            .append("receiveParams", getReceiveParams())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}