package com.cn.hnust.pojo;

/**
 * Created by Administrator_xu on 2017/10/13.
 */
public class DateAndOnceAvgerDistance {
    private String date;
    private long dissum;
    private int vidsum;
    private int trip_count;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getDissum() {
        return dissum;
    }

    public void setDissum(int dissum) {
        this.dissum = dissum;
    }

    public int getVidsum() {
        return vidsum;
    }

    public void setVidsum(int vidsum) {
        this.vidsum = vidsum;
    }

    public int getTrip_count() {
        return trip_count;
    }

    public void setTrip_count(int trip_count) {
        this.trip_count = trip_count;
    }

    @Override
    public String toString() {
        return "DateAndOnceAvgerDistance{" +
                "date='" + date + '\'' +
                ", dissum=" + dissum +
                ", vidsum=" + vidsum +
                ", trip_count=" + trip_count +
                '}';
    }
}
