package com.cn.hnust.pojo;

/**
 * Created by Administrator_xu on 2017/10/13.
 */
public class DistanceTripDist {
    private String travel_distance;
    private int trip_count;

    public String getTravel_distance() {
        return travel_distance;
    }

    public void setTravel_distance(String travel_distance) {
        this.travel_distance = travel_distance;
    }

    public int getTrip_count() {
        return trip_count;
    }

    public void setTrip_count(int trip_count) {
        this.trip_count = trip_count;
    }

    @Override
    public String toString() {
        return "DistanceTripDist{" +
                "travel_distance='" + travel_distance + '\'' +
                ", trip_count='" + trip_count + '\'' +
                '}';
    }
}
