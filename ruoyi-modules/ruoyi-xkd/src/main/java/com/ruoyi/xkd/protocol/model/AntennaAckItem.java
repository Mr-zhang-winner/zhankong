package com.ruoyi.xkd.protocol.model;

import lombok.Data;

@Data
public class AntennaAckItem {
    private int key;
    private int status;
    private String statusName;
}