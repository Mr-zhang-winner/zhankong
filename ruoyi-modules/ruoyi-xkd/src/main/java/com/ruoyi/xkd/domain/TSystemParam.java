package com.ruoyi.xkd.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 系统参数对象 t_system_param
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
@Data
public class TSystemParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 参数ID */
    private Long paramId;

    /** 参数编码 */
    @Excel(name = "参数编码")
    private String paramCode;

    /** 参数名称 */
    @Excel(name = "参数名称")
    private String paramName;

    /** 参数值 */
    @Excel(name = "参数值")
    private String paramValue;

    /** 参数类型：SYSTEM/DEVICE/SATELLITE/ALARM/STORAGE/TASK */
    @Excel(name = "参数类型：SYSTEM/DEVICE/SATELLITE/ALARM/STORAGE/TASK")
    private String paramType;

    /** 参数描述 */
    @Excel(name = "参数描述")
    private String paramDesc;

    /** 状态：0启用 1禁用 */
    @Excel(name = "状态：0启用 1禁用")
    private String status;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("paramId", getParamId())
            .append("paramCode", getParamCode())
            .append("paramName", getParamName())
            .append("paramValue", getParamValue())
            .append("paramType", getParamType())
            .append("paramDesc", getParamDesc())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}