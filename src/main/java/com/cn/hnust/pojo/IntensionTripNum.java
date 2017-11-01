package com.cn.hnust.pojo;

/**
 * Created by Administrator_xu on 2017/10/17.
 */
public class IntensionTripNum {
    private String dates;
    private long vidnum;
    private long tripnum;

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public long getVidnum() {
        return vidnum;
    }

    public void setVidnum(long vidnum) {
        this.vidnum = vidnum;
    }

    public long getTripnum() {
        return tripnum;
    }

    public void setTripnum(long tripnum) {
        this.tripnum = tripnum;
    }

    @Override
    public String toString() {
        return "IntensionTripNum{" +
                "dates='" + dates + '\'' +
                ", vidnum=" + vidnum +
                ", tripnum=" + tripnum +
                '}';
    }
}
