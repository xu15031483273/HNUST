package com.cn.hnust.pojo;

/**
 * Created by Administrator_xu on 2017/10/19.
 */
public class DynamicAction {
    private int shutdown_hour;
    private double shutdown_x;
    private double shutdown_y;
    private int triptype;

    public int getTriptype() {
        return triptype;
    }

    public void setTriptype(int triptype) {
        this.triptype = triptype;
    }

    public int getShutdown_hour() {
        return shutdown_hour;
    }

    public void setShutdown_hour(int shutdown_hour) {
        this.shutdown_hour = shutdown_hour;
    }

    public double getShutdown_x() {
        return shutdown_x;
    }

    public void setShutdown_x(double shutdown_x) {
        this.shutdown_x = shutdown_x;
    }

    public double getShutdown_y() {
        return shutdown_y;
    }

    public void setShutdown_y(double shutdown_y) {
        this.shutdown_y = shutdown_y;
    }
}
