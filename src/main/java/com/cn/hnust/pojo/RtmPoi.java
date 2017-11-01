package com.cn.hnust.pojo;

/**
 * Created by Administrator_xu on 2017/10/12.
 */
public class RtmPoi {
    private String dt;//日期
    private String vid;//车辆编号
    private int tripid;//行程编号
    private int match_type;//匹配类型，1-起点, 2-终点
    private String poi_id;//poi编号
    private String poi_type;//poi类型
    private double confidence;//置信度

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

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

    public int getMatch_type() {
        return match_type;
    }

    public void setMatch_type(int match_type) {
        this.match_type = match_type;
    }

    public String getPoi_id() {
        return poi_id;
    }

    public void setPoi_id(String poi_id) {
        this.poi_id = poi_id;
    }

    public String getPoi_type() {
        return poi_type;
    }

    public void setPoi_type(String poi_type) {
        this.poi_type = poi_type;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "RtmPoi{" +
                "dt='" + dt + '\'' +
                ", vid='" + vid + '\'' +
                ", tripid=" + tripid +
                ", match_type=" + match_type +
                ", poi_id='" + poi_id + '\'' +
                ", poi_type='" + poi_type + '\'' +
                ", confidence=" + confidence +
                '}';
    }
}
