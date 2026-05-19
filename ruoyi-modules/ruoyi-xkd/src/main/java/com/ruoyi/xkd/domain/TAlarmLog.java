package com.ruoyi.xkd.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 告警日志对象 t_alarm_log
 * 
 * @author ruoyi
 * @date 2026-05-06
 */
public class TAlarmLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 告警ID */
    private Long alarmId;

    /** 告警类型：DEVICE/TASK/SYSTEM/NETWORK/ILLEGAL */
    @Excel(name = "告警类型：DEVICE/TASK/SYSTEM/NETWORK/ILLEGAL")
    private String alarmType;

    /** 告警级别：INFO/WARN/ERROR/CRITICAL */
    @Excel(name = "告警级别：INFO/WARN/ERROR/CRITICAL")
    private String alarmLevel;

    /** 告警描述 */
    @Excel(name = "告警描述")
    private String alarmDesc;

    /** 告警位置 */
    @Excel(name = "告警位置")
    private String alarmLocation;

    /** 关联ID：设备ID/任务ID等 */
    @Excel(name = "关联ID：设备ID/任务ID等")
    private String relatedId;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date occurTime;

    /** 处理状态：0未处理 1已确认 2已处理 3已归档 */
    @Excel(name = "处理状态：0未处理 1已确认 2已处理 3已归档")
    private String handleStatus;

    /** 处理人 */
    @Excel(name = "处理人")
    private String handler;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handleTime;

    /** 处理结果 */
    @Excel(name = "处理结果")
    private String handleResult;

    public void setAlarmId(Long alarmId) 
    {
        this.alarmId = alarmId;
    }

    public Long getAlarmId() 
    {
        return alarmId;
    }

    public void setAlarmType(String alarmType) 
    {
        this.alarmType = alarmType;
    }

    public String getAlarmType() 
    {
        return alarmType;
    }

    public void setAlarmLevel(String alarmLevel) 
    {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmLevel() 
    {
        return alarmLevel;
    }

    public void setAlarmDesc(String alarmDesc) 
    {
        this.alarmDesc = alarmDesc;
    }

    public String getAlarmDesc() 
    {
        return alarmDesc;
    }

    public void setAlarmLocation(String alarmLocation) 
    {
        this.alarmLocation = alarmLocation;
    }

    public String getAlarmLocation() 
    {
        return alarmLocation;
    }

    public void setRelatedId(String relatedId) 
    {
        this.relatedId = relatedId;
    }

    public String getRelatedId() 
    {
        return relatedId;
    }

    public void setOccurTime(Date occurTime) 
    {
        this.occurTime = occurTime;
    }

    public Date getOccurTime() 
    {
        return occurTime;
    }

    public void setHandleStatus(String handleStatus) 
    {
        this.handleStatus = handleStatus;
    }

    public String getHandleStatus() 
    {
        return handleStatus;
    }

    public void setHandler(String handler) 
    {
        this.handler = handler;
    }

    public String getHandler() 
    {
        return handler;
    }

    public void setHandleTime(Date handleTime) 
    {
        this.handleTime = handleTime;
    }

    public Date getHandleTime() 
    {
        return handleTime;
    }

    public void setHandleResult(String handleResult) 
    {
        this.handleResult = handleResult;
    }

    public String getHandleResult() 
    {
        return handleResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("alarmId", getAlarmId())
            .append("alarmType", getAlarmType())
            .append("alarmLevel", getAlarmLevel())
            .append("alarmDesc", getAlarmDesc())
            .append("alarmLocation", getAlarmLocation())
            .append("relatedId", getRelatedId())
            .append("occurTime", getOccurTime())
            .append("handleStatus", getHandleStatus())
            .append("handler", getHandler())
            .append("handleTime", getHandleTime())
            .append("handleResult", getHandleResult())
            .append("createTime", getCreateTime())
            .toString();
    }
}
