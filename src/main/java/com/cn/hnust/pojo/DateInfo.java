package com.cn.hnust.pojo;

/**
 * Created by Administrator_xu on 2017/10/17.
 */
public class DateInfo {
    private String dt;
    private String dayOfWeek;
    private String restriction;
    private String dateinfo;
    private String datekind;
    private String weekOfYear;
    private String monthOfYear;
    private String quarterOfYear;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    public String getDateinfo() {
        return dateinfo;
    }

    public void setDateinfo(String dateinfo) {
        this.dateinfo = dateinfo;
    }

    public String getDatekind() {
        return datekind;
    }

    public void setDatekind(String datekind) {
        this.datekind = datekind;
    }

    public String getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(String weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public String getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(String monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public String getQuarterOfYear() {
        return quarterOfYear;
    }

    public void setQuarterOfYear(String quarterOfYear) {
        this.quarterOfYear = quarterOfYear;
    }

    @Override
    public String toString() {
        return "DateInfo{" +
                "dt='" + dt + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", restriction='" + restriction + '\'' +
                ", dateinfo='" + dateinfo + '\'' +
                ", datekind='" + datekind + '\'' +
                ", weekOfYear='" + weekOfYear + '\'' +
                ", monthOfYear='" + monthOfYear + '\'' +
                ", quarterOfYear='" + quarterOfYear + '\'' +
                '}';
    }
}
