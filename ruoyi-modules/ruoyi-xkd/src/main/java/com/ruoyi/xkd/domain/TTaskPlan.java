package com.ruoyi.xkd.domain;

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
public class TTaskPlan extends BaseEntity
{
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

    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }

    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }

    public void setTaskType(String taskType) 
    {
        this.taskType = taskType;
    }

    public String getTaskType() 
    {
        return taskType;
    }

    public void setPeriod(String period) 
    {
        this.period = period;
    }

    public String getPeriod() 
    {
        return period;
    }

    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }

    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }

    public void setSatelliteId(Long satelliteId) 
    {
        this.satelliteId = satelliteId;
    }

    public Long getSatelliteId() 
    {
        return satelliteId;
    }

    public void setStationId(Long stationId) 
    {
        this.stationId = stationId;
    }

    public Long getStationId() 
    {
        return stationId;
    }

    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }

    public void setParamConfig(String paramConfig) 
    {
        this.paramConfig = paramConfig;
    }

    public String getParamConfig() 
    {
        return paramConfig;
    }

    public void setExecuteStatus(String executeStatus) 
    {
        this.executeStatus = executeStatus;
    }

    public String getExecuteStatus() 
    {
        return executeStatus;
    }

    public void setConflictStatus(String conflictStatus) 
    {
        this.conflictStatus = conflictStatus;
    }

    public String getConflictStatus() 
    {
        return conflictStatus;
    }

    public void setConflictReason(String conflictReason) 
    {
        this.conflictReason = conflictReason;
    }

    public String getConflictReason() 
    {
        return conflictReason;
    }

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
