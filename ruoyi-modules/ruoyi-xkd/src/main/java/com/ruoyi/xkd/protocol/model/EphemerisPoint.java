package com.ruoyi.xkd.protocol.model;

public class EphemerisPoint
{
    private long gpsWeek;
    private long gpsSecond;
    private double x;
    private double y;
    private double z;
    private double vx;
    private double vy;
    private double vz;
    private int dataSeq;

    public long getGpsWeek() { return gpsWeek; }
    public void setGpsWeek(long gpsWeek) { this.gpsWeek = gpsWeek; }

    public long getGpsSecond() { return gpsSecond; }
    public void setGpsSecond(long gpsSecond) { this.gpsSecond = gpsSecond; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getZ() { return z; }
    public void setZ(double z) { this.z = z; }

    public double getVx() { return vx; }
    public void setVx(double vx) { this.vx = vx; }

    public double getVy() { return vy; }
    public void setVy(double vy) { this.vy = vy; }

    public double getVz() { return vz; }
    public void setVz(double vz) { this.vz = vz; }

    public int getDataSeq() { return dataSeq; }
    public void setDataSeq(int dataSeq) { this.dataSeq = dataSeq; }
}