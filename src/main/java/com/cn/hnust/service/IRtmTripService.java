package com.cn.hnust.service;

import com.cn.hnust.pojo.SpeedWithTriplen;
import com.cn.hnust.pojo.*;

import java.util.List;
import java.util.Map;

public interface IRtmTripService {
	public RtmTrip getDemoById();
	//speedWithTripTime
	public List<DateInfo> getDateInfo(Map<String, Object> map);

	//出行距离
	public List<DateAndOnceAvgerDistance> getDateAndOnceAvgerDistance(Map<String, Object> map);

	public List<DistanceTripDist> getDistanceTripDist(Map<String, Object> map);

	public List<DaySumVid> getDaySumTripDistance(Map<String, Object> map);

	public List<DisAvgConsume> getDisAvgConsume(Map<String, Object> map);
	//出行时耗
	public List<TimeTripTime> getTimeTripTime(Map<String, Object> map);

	public List<DistanceTripDist> getTimeConsumeDist(Map<String, Object> map);
	//	getAvgCarConsume
	public List<DaySumVid> getAvgCarConsume(Map<String, Object> map);
	//getDisAvgConsumeTime
	public List<DisAvgConsume> timeAvgConsume(Map<String, Object> map);
	//SpeedWithTriplen
	public List<SpeedWithTriplen> speedWithTriplen(Map<String, Object> map);
	//speedWithTripTime
	public List<SpeedWithTriplen> speedWithTripTime(Map<String, Object> map);

	//出行强度
	public List<IntensionTripNum> intensionTripNum(Map<String, Object> map);

	public List<IntensionTripNum> intensionTripNumDay(Map<String, Object> map);
//	intensionTripRate
	public List<IntensionTripNum> intensionTripRate(Map<String, Object> map);
	//intensionTripRateMax
	public List<IntensionTripNum> intensionTripRateMax(Map<String, Object> map);

	//dynamicAction
	public List<DynamicAction> dynamicAction(Map<String, Object> map);
	//dynamicActionDaoTime
	public List<DynamicActionTime> dynamicActionDaoTime(Map<String, Object> map);
	//selectQyhfStartup
	public List<SelectQyhfStartup> selectQyhfStartup(Map<String, Object> map);
	//dayAvgStopCar
	public List<DynamicActionTime> dayAvgStopCar(Map<String, Object> map);

	//stopCarType
	public List<StopCarType> stopCarType(Map<String, Object> map);
	//StopIntension
	public List<StopCarTypePart> StopIntension(Map<String, Object> map);

}
