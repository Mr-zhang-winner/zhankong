package com.ruoyi.xkd.domain;

import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 设备控制日志对象 t_device_control_log
 * 
 * @author ruoyi
 * @date 2026-05-11
 */
@Data
public class TDeviceControlLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 控制ID */
    private Long controlId;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long deviceId;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceCode;

    /** 命令类型 */
    @Excel(name = "命令类型")
    private String commandType;

    /** 协议KEY */
    @Excel(name = "协议KEY")
    private String commandKey;

    /** 命令内容JSON */
    @Excel(name = "命令内容JSON")
    private String commandContent;

    /** 下发命令码 */
    @Excel(name = "下发命令码")
    private String cmdCode;

    /** 下发帧HEX */
    @Excel(name = "下发帧HEX")
    private String frameHex;

    /** 应答命令码 */
    @Excel(name = "应答命令码")
    private String ackCmd;

    /** ACK状态 */
    @Excel(name = "ACK状态")
    private String ackStatus;

    /** ACK内容JSON */
    @Excel(name = "ACK内容JSON")
    private String ackContent;

    /** 发送状态：WAITING/SUCCESS/FAILED/TIMEOUT */
    @Excel(name = "发送状态：WAITING/SUCCESS/FAILED/TIMEOUT")
    private String sendStatus;

    /** 发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendTime;

    /** 响应时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "响应时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date ackTime;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    /** 错误信息 */
    @Excel(name = "错误信息")
    private String errorMsg;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("controlId", getControlId())
            .append("deviceId", getDeviceId())
            .append("deviceCode", getDeviceCode())
            .append("commandType", getCommandType())
            .append("commandKey", getCommandKey())
            .append("commandContent", getCommandContent())
            .append("cmdCode", getCmdCode())
            .append("frameHex", getFrameHex())
            .append("ackCmd", getAckCmd())
            .append("ackStatus", getAckStatus())
            .append("ackContent", getAckContent())
            .append("sendStatus", getSendStatus())
            .append("sendTime", getSendTime())
            .append("ackTime", getAckTime())
            .append("operator", getOperator())
            .append("errorMsg", getErrorMsg())
            .append("createTime", getCreateTime())
            .toString();
    }
}