package com.cn.hnust.service;

import com.cn.hnust.pojo.Blowoff;
import com.cn.hnust.pojo.MapJson;

import java.util.List;

public interface BlowoffService {
	public List<Blowoff> getRtmPoiById();
	//selectBlowoffMap

	public List<MapJson> selectBlowoffMap();
}
