package com.cn.hnust.service;

import com.cn.hnust.pojo.Demo;

public interface IDemoService {
	public Demo getDemoById(int userId);
	public void insertDemo(Demo demo);
	public void insertSelective(Demo demo);
	public void update(Demo demo);
	public void updateByPrimaryKey(Demo demo);
}
