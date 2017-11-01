package com.cn.hnust.dao;

import com.cn.hnust.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.json.simple.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator_xu on 2017/10/12.
 */
public interface IRtmTripDao {
    RtmTrip selectAll();
    //是否是工作日
    List<DateInfo> getDateInfo(Map<String, Object> map);

    //出行距离
    List<DateAndOnceAvgerDistance> getDateAndOnceAvgerDistance(Map<String, Object> map);

    List<DistanceTripDist> getDistanceTripDist(Map<String, Object> map);

    List<DaySumVid> getDaySumTripDistance(Map<String, Object> map);

    List<DisAvgConsume> getDisAvgConsume(Map<String, Object> map);

    //出行时耗
    List<TimeTripTime> getTimeTripTime(Map<String, Object> map);

    List<DistanceTripDist> getTimeConsumeDist(Map<String, Object> map);
    //getAvgCarConsume
    List<DaySumVid> getAvgCarConsume(Map<String, Object> map);
    //getDisAvgConsume
    List<DisAvgConsume> timeAvgConsume(Map<String, Object> map);
    //SpeedWithTriplen
    List<SpeedWithTriplen> speedWithTriplen(Map<String, Object> map);
    //speedWithTripTime
    List<SpeedWithTriplen> speedWithTripTime(Map<String, Object> map);
    //出行强度
    List<IntensionTripNum> intensionTripNum(Map<String, Object> map);
    //出行强度
    List<IntensionTripNum> intensionTripNumDay(Map<String, Object> map);
    //出车率
    List<IntensionTripNum> intensionTripRate(Map<String, Object> map);
    //intensionTripRateMax
    List<IntensionTripNum> intensionTripRateMax(Map<String, Object> map);

    //stop
    List<DynamicAction> dynamicActionDao(Map<String, Object> map);
    //stop
    List<DynamicActionTime> dynamicActionDaoTime(Map<String, Object> map);
    //selectQyhfStartup
    List<SelectQyhfStartup> selectQyhfStartup(Map<String, Object> map);
    //dayAvgStopCar
    List<DynamicActionTime> dayAvgStopCar(Map<String, Object> map);
    //stopCarType
    List<StopCarType> stopCarType(Map<String, Object> map);
    //StopIntension
    List<StopCarTypePart> StopIntension(Map<String, Object> map);
}
