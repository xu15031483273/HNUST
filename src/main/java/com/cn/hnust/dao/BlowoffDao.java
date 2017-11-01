package com.cn.hnust.dao;

import com.cn.hnust.pojo.Blowoff;
import com.cn.hnust.pojo.MapJson;
import com.cn.hnust.pojo.RtmPoi;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator_xu on 2017/10/12.
 */
public interface BlowoffDao {
    List<Blowoff> selectBlowoff();

    List<MapJson> selectBlowoffMap();
}
