package com.cn.hnust.pojo;

/**
 * Created by Administrator_xu on 2017/10/14.
 */
public class DisAvgConsume {
    private String hourofday;
    private double distum;
    private long trip_count;

    public String getHourofday() {
        return hourofday;
    }

    public void setHourofday(String hourofday) {
        this.hourofday = hourofday;
    }

    public double getDistum() {
        return distum;
    }

    public void setDistum(double distum) {
        this.distum = distum;
    }

    public long getTrip_count() {
        return trip_count;
    }

    public void setTrip_count(long trip_count) {
        this.trip_count = trip_count;
    }

    @Override
    public String toString() {
        return "DisAvgConsume{" +
                "hourofday='" + hourofday + '\'' +
                ", distum=" + distum +
                ", trip_count=" + trip_count +
                '}';
    }
}
