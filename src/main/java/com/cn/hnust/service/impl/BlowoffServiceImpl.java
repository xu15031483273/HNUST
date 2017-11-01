package com.cn.hnust.service.impl;

import com.cn.hnust.dao.BlowoffDao;
import com.cn.hnust.dao.IRtmPoiDao;
import com.cn.hnust.pojo.Blowoff;
import com.cn.hnust.pojo.MapJson;
import com.cn.hnust.pojo.RtmPoi;
import com.cn.hnust.service.BlowoffService;
import com.cn.hnust.service.IRtmPoiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator_xu on 2017/10/12.
 */
@Service("blowoffService")
public class BlowoffServiceImpl implements BlowoffService {
    @Resource
    private BlowoffDao blowoffDao;

    @Override
    public List<Blowoff> getRtmPoiById() {
        return this.blowoffDao.selectBlowoff();
    }

    @Override
    public List<MapJson> selectBlowoffMap() {
        return this.blowoffDao.selectBlowoffMap();
    }
}
