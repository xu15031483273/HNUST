package com.cn.hnust.service.impl;

import com.cn.hnust.dao.IRtmTripDao;
import com.cn.hnust.pojo.SpeedWithTriplen;
import com.cn.hnust.pojo.*;
import com.cn.hnust.service.IRtmTripService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator_xu on 2017/10/12.
 */
@Service("rtmTripService")
public class RtmTripServiceImpl implements IRtmTripService {
    @Resource
    private IRtmTripDao rtmTripDao;
    @Override
    public RtmTrip getDemoById() {
        return this.rtmTripDao.selectAll();
    }

    @Override
    public List<DateInfo> getDateInfo(Map<String, Object> map) {
        return this.rtmTripDao.getDateInfo(map);
    }

    @Override
    public List getDateAndOnceAvgerDistance(Map<String, Object> map) {
        return this.rtmTripDao.getDateAndOnceAvgerDistance(map);
    }

    @Override
    public List<DistanceTripDist> getDistanceTripDist(Map<String, Object> map) {
        return this.rtmTripDao.getDistanceTripDist(map);
    }

    @Override
    public List<DaySumVid> getDaySumTripDistance(Map<String, Object> map) {
        return this.rtmTripDao.getDaySumTripDistance(map);
    }

    @Override
    public List<DisAvgConsume> getDisAvgConsume(Map<String, Object> map) {
        return this.rtmTripDao.getDisAvgConsume(map);
    }

    @Override
    public List<TimeTripTime> getTimeTripTime(Map<String, Object> map) {
        return this.rtmTripDao.getTimeTripTime(map);
    }

    @Override
    public List<DistanceTripDist> getTimeConsumeDist(Map<String, Object> map) {
        return this.rtmTripDao.getTimeConsumeDist(map);
    }
    //getAvgCarConsume
    @Override
    public List<DaySumVid> getAvgCarConsume(Map<String, Object> map) {
        return this.rtmTripDao.getAvgCarConsume(map);
    }
    @Override
    public List<DisAvgConsume> timeAvgConsume(Map<String, Object> map) {
        return this.rtmTripDao.timeAvgConsume(map);
    }
    @Override
    public List<SpeedWithTriplen> speedWithTriplen(Map<String, Object> map) {
        return this.rtmTripDao.speedWithTriplen(map);
    }
    //speedWithTripTime
    @Override
    public List<SpeedWithTriplen> speedWithTripTime(Map<String, Object> map) {
        return this.rtmTripDao.speedWithTripTime(map);
    }

    @Override
    public List<IntensionTripNum> intensionTripNum(Map<String, Object> map) {
        return this.rtmTripDao.intensionTripNum(map);
    }

    @Override
    public List<IntensionTripNum> intensionTripNumDay(Map<String, Object> map) {
        return this.rtmTripDao.intensionTripNumDay(map);
    }

    @Override
    public List<IntensionTripNum> intensionTripRate(Map<String, Object> map) {
        return this.rtmTripDao.intensionTripRate(map);
    }
    //intensionTripRateMax
    @Override
    public List<IntensionTripNum> intensionTripRateMax(Map<String, Object> map) {
        return this.rtmTripDao.intensionTripRateMax(map);
    }
    //dynamicAction
    @Override
    public List<DynamicAction> dynamicAction(Map<String, Object> map) {
        return this.rtmTripDao.dynamicActionDao(map);
    }
    //dynamicActionDaoTime
    @Override
    public List<DynamicActionTime> dynamicActionDaoTime(Map<String, Object> map) {
        return this.rtmTripDao.dynamicActionDaoTime(map);
    }

    @Override
    public List<SelectQyhfStartup> selectQyhfStartup(Map<String, Object> map) {
        return this.rtmTripDao.selectQyhfStartup(map);
    }
    //dayAvgStopCar
    @Override
    public List<DynamicActionTime> dayAvgStopCar(Map<String, Object> map) {
        return this.rtmTripDao.dayAvgStopCar(map);
    }

    //stopCarType
    @Override
    public List<StopCarType> stopCarType(Map<String, Object> map) {
        return this.rtmTripDao.stopCarType(map);
    }
    //StopIntension
    @Override
    public List<StopCarTypePart> StopIntension(Map<String, Object> map) {
        return this.rtmTripDao.StopIntension(map);
    }
}
