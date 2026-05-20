package com.ruoyi.xkd.protocol.model;

import lombok.Data;

@Data
public class AntennaFrame {
    private int cmd;
    private int length;
    private byte[] payload;
    private int checksum;
    private byte[] rawFrame;
    private String remoteIp;
    private Integer remotePort;
    private Long deviceId;
    private String deviceCode;
    private String deviceName;
}