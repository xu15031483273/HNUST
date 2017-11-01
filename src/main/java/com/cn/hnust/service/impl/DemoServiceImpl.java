package com.cn.hnust.service.impl;

import com.cn.hnust.dao.IDemoDao;
import com.cn.hnust.pojo.Demo;
import com.cn.hnust.service.IDemoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator_xu on 2017/10/12.
 */
@Service("demoService")
public class DemoServiceImpl implements IDemoService {
    @Resource
    private IDemoDao demoDao;

    @Override
    public Demo getDemoById(int userId) {
        return this.demoDao.selectByPrimaryKey(userId);
    }

    @Override
    public void insertDemo(Demo demo) {
       this.demoDao.insertDemo(demo);
    }

    @Override
    public void insertSelective(Demo demo) {
        this.demoDao.insertSelective(demo);
    }

    @Override
    public void update(Demo demo) {
        this.demoDao.updateByPrimaryKeySelective(demo);
    }

    @Override
    public void updateByPrimaryKey(Demo demo) {
        this.demoDao.updateByPrimaryKey(demo);
    }


}
