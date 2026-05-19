package com.ruoyi.xkd.protocol.model;

public class AntennaParam
{
    private int key;
    private int len;
    private byte[] value;

    public AntennaParam() {}

    public AntennaParam(int key, int len, byte[] value)
    {
        this.key = key;
        this.len = len;
        this.value = value;
    }

    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }

    public int getLen() { return len; }
    public void setLen(int len) { this.len = len; }

    public byte[] getValue() { return value; }
    public void setValue(byte[] value) { this.value = value; }
}