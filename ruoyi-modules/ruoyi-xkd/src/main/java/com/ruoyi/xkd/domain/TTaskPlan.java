package com.ruoyi.xkd.domain;

import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 任务计划对象 t_task_plan
 * 
 * @author ruoyi
 * @date 2026-05-08
 */
@Data
public class TTaskPlan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private Long taskId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 任务类型：MANUAL/AUTO */
    @Excel(name = "任务类型：MANUAL/AUTO")
    private String taskType;

    /** 任务周期 */
    @Excel(name = "任务周期")
    private String period;

    /** 任务开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 任务结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 卫星ID */
    @Excel(name = "卫星ID")
    private Long satelliteId;

    /** 站址ID */
    @Excel(name = "站址ID")
    private Long stationId;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long deviceId;

    /** 任务参数JSON */
    @Excel(name = "任务参数JSON")
    private String paramConfig;

    /** 执行状态：WAITING/RUNNING/FINISHED/CANCELLED */
    @Excel(name = "执行状态：WAITING/RUNNING/FINISHED/CANCELLED")
    private String executeStatus;

    /** 冲突状态：0无冲突 1有冲突 */
    @Excel(name = "冲突状态：0无冲突 1有冲突")
    private String conflictStatus;

    /** 冲突原因 */
    @Excel(name = "冲突原因")
    private String conflictReason;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("taskName", getTaskName())
            .append("taskType", getTaskType())
            .append("period", getPeriod())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("satelliteId", getSatelliteId())
            .append("stationId", getStationId())
            .append("deviceId", getDeviceId())
            .append("paramConfig", getParamConfig())
            .append("executeStatus", getExecuteStatus())
            .append("conflictStatus", getConflictStatus())
            .append("conflictReason", getConflictReason())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}