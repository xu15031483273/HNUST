package org.zsl.testmybatis;

import javax.annotation.Resource;

import com.cn.hnust.pojo.Demo;
import com.cn.hnust.pojo.RtmTrip;
import com.cn.hnust.service.IDemoService;
import com.cn.hnust.service.IRtmTripService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestMyBatis {
	private static Logger logger = Logger.getLogger(TestMyBatis.class);
//	private ApplicationContext ac = null;
	@Resource
	private IDemoService demoService=null;
	@Resource
	private IRtmTripService rtmTripService=null;


//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}

	@Test
	public void test3() {
		Demo demoid = demoService.getDemoById(1);
		Demo demo=new Demo();
		demo.setId(4);
//		demo.setAge(12);
		demo.setPassword("update234234234");
		demo.setUserName("l234242343alalal");
//		demoService.insertDemo(demo);
//		demoService.insertSelective(demo);
//		demoService.update(demo);
		demoService.updateByPrimaryKey(demo);
		logger.info(JSON.toJSONString(demoid));
	}

	@Test
	public void rtmTrip() {
		RtmTrip rtmTrip= rtmTripService.getDemoById();
		// System.out.println(user.getUserName());
		// logger.info("值："+user.getUserName());
		logger.info(JSON.toJSONString(rtmTrip));
	}


}
