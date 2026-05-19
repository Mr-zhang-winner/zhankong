package com.ruoyi.xkd.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 天线协议收发日志对象 t_antenna_protocol_log
 * 
 * @author ruoyi
 * @date 2026-05-11
 */
public class TAntennaProtocolLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志ID */
    private Long logId;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceCode;

    /** 方向：SEND/RECEIVE */
    @Excel(name = "方向：SEND/RECEIVE")
    private String direction;

    /** 远端IP */
    @Excel(name = "远端IP")
    private String remoteIp;

    /** 远端端口 */
    @Excel(name = "远端端口")
    private Long remotePort;

    /** 命令码 */
    @Excel(name = "命令码")
    private String cmdCode;

    /** 命令名称 */
    @Excel(name = "命令名称")
    private String cmdName;

    /** 完整原始帧HEX */
    @Excel(name = "完整原始帧HEX")
    private String frameHex;

    /** 参数体HEX */
    @Excel(name = "参数体HEX")
    private String payloadHex;

    /** 解析后的内容JSON */
    @Excel(name = "解析后的内容JSON")
    private String payloadJson;

    /** 校验状态：OK/FAILED */
    @Excel(name = "校验状态：OK/FAILED")
    private String checkStatus;

    /** 结果状态 */
    @Excel(name = "结果状态")
    private String resultStatus;

    /** 错误信息 */
    @Excel(name = "错误信息")
    private String errorMsg;

    public void setLogId(Long logId) 
    {
        this.logId = logId;
    }

    public Long getLogId() 
    {
        return logId;
    }

    public void setDeviceCode(String deviceCode) 
    {
        this.deviceCode = deviceCode;
    }

    public String getDeviceCode() 
    {
        return deviceCode;
    }

    public void setDirection(String direction) 
    {
        this.direction = direction;
    }

    public String getDirection() 
    {
        return direction;
    }

    public void setRemoteIp(String remoteIp) 
    {
        this.remoteIp = remoteIp;
    }

    public String getRemoteIp() 
    {
        return remoteIp;
    }

    public void setRemotePort(Long remotePort) 
    {
        this.remotePort = remotePort;
    }

    public Long getRemotePort() 
    {
        return remotePort;
    }

    public void setCmdCode(String cmdCode) 
    {
        this.cmdCode = cmdCode;
    }

    public String getCmdCode() 
    {
        return cmdCode;
    }

    public void setCmdName(String cmdName) 
    {
        this.cmdName = cmdName;
    }

    public String getCmdName() 
    {
        return cmdName;
    }

    public void setFrameHex(String frameHex) 
    {
        this.frameHex = frameHex;
    }

    public String getFrameHex() 
    {
        return frameHex;
    }

    public void setPayloadHex(String payloadHex) 
    {
        this.payloadHex = payloadHex;
    }

    public String getPayloadHex() 
    {
        return payloadHex;
    }

    public void setPayloadJson(String payloadJson) 
    {
        this.payloadJson = payloadJson;
    }

    public String getPayloadJson() 
    {
        return payloadJson;
    }

    public void setCheckStatus(String checkStatus) 
    {
        this.checkStatus = checkStatus;
    }

    public String getCheckStatus() 
    {
        return checkStatus;
    }

    public void setResultStatus(String resultStatus) 
    {
        this.resultStatus = resultStatus;
    }

    public String getResultStatus() 
    {
        return resultStatus;
    }

    public void setErrorMsg(String errorMsg) 
    {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() 
    {
        return errorMsg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logId", getLogId())
            .append("deviceCode", getDeviceCode())
            .append("direction", getDirection())
            .append("remoteIp", getRemoteIp())
            .append("remotePort", getRemotePort())
            .append("cmdCode", getCmdCode())
            .append("cmdName", getCmdName())
            .append("frameHex", getFrameHex())
            .append("payloadHex", getPayloadHex())
            .append("payloadJson", getPayloadJson())
            .append("checkStatus", getCheckStatus())
            .append("resultStatus", getResultStatus())
            .append("errorMsg", getErrorMsg())
            .append("createTime", getCreateTime())
            .toString();
    }
}
