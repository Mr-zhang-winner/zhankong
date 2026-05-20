package com.ruoyi.xkd.protocol.model;

import lombok.Data;

@Data
public class EphemerisPoint {
    private long gpsWeek;
    private long gpsSecond;
    private double x;
    private double y;
    private double z;
    private double vx;
    private double vy;
    private double vz;
    private int dataSeq;
}