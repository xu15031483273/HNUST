package com.cn.hnust.pojo;

/**
 * Created by Administrator_xu on 2017/10/23.
 */
public class StopCarType {
    private int shutdown_hour;
    private long countall;
    private int downhour;
    private String datetime;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getShutdown_hour() {
        return shutdown_hour;
    }

    public void setShutdown_hour(int shutdown_hour) {
        this.shutdown_hour = shutdown_hour;
    }

    public long getCountall() {
        return countall;
    }

    public void setCountall(long countall) {
        this.countall = countall;
    }

    public int getDownhour() {
        return downhour;
    }

    public void setDownhour(int downhour) {
        this.downhour = downhour;
    }
}
