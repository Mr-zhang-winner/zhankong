package com.ruoyi.xkd.protocol.model;

public class AntennaFrame
{
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

    public int getCmd() { return cmd; }
    public void setCmd(int cmd) { this.cmd = cmd; }

    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }

    public byte[] getPayload() { return payload; }
    public void setPayload(byte[] payload) { this.payload = payload; }

    public int getChecksum() { return checksum; }
    public void setChecksum(int checksum) { this.checksum = checksum; }

    public byte[] getRawFrame() { return rawFrame; }
    public void setRawFrame(byte[] rawFrame) { this.rawFrame = rawFrame; }

    public String getRemoteIp() { return remoteIp; }
    public void setRemoteIp(String remoteIp) { this.remoteIp = remoteIp; }

    public Integer getRemotePort() { return remotePort; }
    public void setRemotePort(Integer remotePort) { this.remotePort = remotePort; }

    public String getDeviceCode() { return deviceCode; }
    public void setDeviceCode(String deviceCode) { this.deviceCode = deviceCode; }

    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }

    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
}