package com.cn.hnust.pojo;

/**
 * Created by Administrator_xu on 2017/10/16.
 */
public class DaySumVid {
    private String dateofday;
    private String vid;
    private int dissum;

    public String getDateofday() {
        return dateofday;
    }

    public void setDateofday(String dateofday) {
        this.dateofday = dateofday;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public int getDissum() {
        return dissum;
    }

    public void setDissum(int dissum) {
        this.dissum = dissum;
    }

    @Override
    public String toString() {
        return "DaySumVid{" +
                "dateofday='" + dateofday + '\'' +
                ", vid='" + vid + '\'' +
                ", dissum=" + dissum +
                '}';
    }
}
