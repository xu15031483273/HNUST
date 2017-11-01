package com.cn.hnust.service.impl;

import com.cn.hnust.dao.IRtmPoiDao;
import com.cn.hnust.pojo.RtmPoi;
import com.cn.hnust.service.IRtmPoiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator_xu on 2017/10/12.
 */
@Service("rtmPoiService")
public class RtmPoiServiceImpl implements IRtmPoiService {
    @Resource
    private IRtmPoiDao rtmPoiDao;

    @Override
    public RtmPoi getRtmPoiById() {
        return this.rtmPoiDao.selectAll();
    }
}
