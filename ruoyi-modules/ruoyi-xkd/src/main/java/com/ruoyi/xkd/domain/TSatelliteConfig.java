package com.ruoyi.xkd.domain;

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
public class TSatelliteConfig extends BaseEntity
{
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

    public void setSatelliteId(Long satelliteId) 
    {
        this.satelliteId = satelliteId;
    }

    public Long getSatelliteId() 
    {
        return satelliteId;
    }

    public void setSatelliteCode(String satelliteCode) 
    {
        this.satelliteCode = satelliteCode;
    }

    public String getSatelliteCode() 
    {
        return satelliteCode;
    }

    public void setSatelliteName(String satelliteName) 
    {
        this.satelliteName = satelliteName;
    }

    public String getSatelliteName() 
    {
        return satelliteName;
    }

    public void setEphemerisType(String ephemerisType) 
    {
        this.ephemerisType = ephemerisType;
    }

    public String getEphemerisType() 
    {
        return ephemerisType;
    }

    public void setOrbitParams(String orbitParams) 
    {
        this.orbitParams = orbitParams;
    }

    public String getOrbitParams() 
    {
        return orbitParams;
    }

    public void setReceiveParams(String receiveParams) 
    {
        this.receiveParams = receiveParams;
    }

    public String getReceiveParams() 
    {
        return receiveParams;
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
