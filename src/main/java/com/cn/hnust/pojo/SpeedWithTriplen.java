package com.cn.hnust.pojo;

/**
 * Created by Administrator_xu on 2017/10/16.
 */
public class SpeedWithTriplen {
    private double distance;

    private double speed;

    public double getTriplen() {
        return distance;
    }

    public void setTriplen(double triplen) {
        this.distance = triplen;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "SpeedWithTriplen{" +
                "triplen=" + distance +
                ", speed=" + speed +
                '}';
    }
}
