package com.ruoyi.xkd.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Map;

import lombok.Data;

/**
 * 协议解析结果 DTO
 * 用于 WebSocket 推送协议解析后的结构化数据
 */
@Data
public class ProtocolParseResult {

    /** 命令码（如 0x83） */
    private String cmdCode;

    /** 命令名称（如 CMD_QUERY_ACK） */
    private String cmdName;

    /** 设备编号 */
    private String deviceCode;

    /** 方向（SEND/RECEIVE） */
    private String direction;

    /** 远端IP */
    private String remoteIp;

    /** 远端端口 */
    private Integer remotePort;

    /** 解析后的参数键值对 */
    private Map<String, Object> params;

    /** 解析时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date parseTime;

    /** 校验状态（OK/FAILED） */
    private String checkStatus;

    /** 原始帧HEX */
    private String frameHex;

    /** payload HEX */
    private String payloadHex;
}