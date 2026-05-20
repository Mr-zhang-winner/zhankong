package com.ruoyi.xkd.protocol.model;

import lombok.Data;

@Data
public class AntennaParam {
    private int key;
    private int len;
    private byte[] value;

    public AntennaParam() {}

    public AntennaParam(int key, int len, byte[] value) {
        this.key = key;
        this.len = len;
        this.value = value;
    }
}