package com.cn.hnust.dao;

import com.cn.hnust.pojo.Demo;

/**
 * Created by Administrator_xu on 2017/10/12.
 */
public interface IDemoDao {
    Demo selectByPrimaryKey(Integer id);
    int insertDemo(Demo record);
    int insertSelective(Demo record);
    int updateByPrimaryKeySelective(Demo demo);
    int updateByPrimaryKey(Demo demo);
}
