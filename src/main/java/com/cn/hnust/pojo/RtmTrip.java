package com.cn.hnust.pojo;

import java.util.Date;

/**
 * Created by Administrator_xu on 2017/10/12.
 */
public class RtmTrip {
    private String vid;//车辆编号
    private int tripid;//行程编号，yyyyMMdd%04d
    private int trip_type;//行程状态，1-移动, 2-停车
    private String dt;//日期
    private int record_count;//记录数量
    private double distance;//旅行距离，单位m
    private int elapse;//旅行时间，单位s
    private double speed;//速度，单位km/h
    private double speed_max;//最大速度，单位km/h
    private String start_time;//开始时间
    private double start_x;//开始点x坐标
    private double start_y;//开始点y坐标
    private String end_time;//结束时间
    private double end_x;//结束点x坐标
    private double end_y;//结束点y坐标

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public int getTripid() {
        return tripid;
    }

    public void setTripid(int tripid) {
        this.tripid = tripid;
    }

    public int getTrip_type() {
        return trip_type;
    }

    public void setTrip_type(int trip_type) {
        this.trip_type = trip_type;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public int getRecord_count() {
        return record_count;
    }

    public void setRecord_count(int record_count) {
        this.record_count = record_count;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getElapse() {
        return elapse;
    }

    public void setElapse(int elapse) {
        this.elapse = elapse;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed_max() {
        return speed_max;
    }

    public void setSpeed_max(double speed_max) {
        this.speed_max = speed_max;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public double getStart_x() {
        return start_x;
    }

    public void setStart_x(double start_x) {
        this.start_x = start_x;
    }

    public double getStart_y() {
        return start_y;
    }

    public void setStart_y(double start_y) {
        this.start_y = start_y;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public double getEnd_x() {
        return end_x;
    }

    public void setEnd_x(double end_x) {
        this.end_x = end_x;
    }

    public double getEnd_y() {
        return end_y;
    }

    public void setEnd_y(double end_y) {
        this.end_y = end_y;
    }

    @Override
    public String toString() {
        return "RtmTrip{" +
                "vid='" + vid + '\'' +
                ", tripid=" + tripid +
                ", trip_type=" + trip_type +
                ", dt=" + dt +
                ", record_count=" + record_count +
                ", distance=" + distance +
                ", elapse=" + elapse +
                ", speed=" + speed +
                ", speed_max=" + speed_max +
                ", start_time='" + start_time + '\'' +
                ", start_x=" + start_x +
                ", start_y=" + start_y +
                ", end_time='" + end_time + '\'' +
                ", end_x=" + end_x +
                ", end_y=" + end_y +
                '}';
    }
}
