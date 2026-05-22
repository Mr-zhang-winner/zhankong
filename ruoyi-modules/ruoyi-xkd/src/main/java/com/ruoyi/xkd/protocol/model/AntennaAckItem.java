package com.ruoyi.xkd.protocol.model;

public class AntennaAckItem
{
    private int key;
    private int status;
    private String statusName;

    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getStatusName() { return statusName; }
    public void setStatusName(String statusName) { this.statusName = statusName; }
}