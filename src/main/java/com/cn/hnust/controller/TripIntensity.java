package com.cn.hnust.controller;

import com.cn.hnust.pojo.IntensionTripNum;
import com.cn.hnust.service.IRtmTripService;
import org.apache.commons.collections.map.HashedMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出行强度统计
 * Created by Administrator_xu on 2017/10/17.
 */
@Controller
public class TripIntensity {
    private Logger logger  =  LoggerFactory.getLogger(RtmTripController.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sms = new SimpleDateFormat("HH:mm:ss");
    @Resource
    private IRtmTripService rtmTripService;
    //车均日总出行
    @ResponseBody
    @RequestMapping("/intensionTripNum.do")
    public void intensionTripNum(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");
        String period  =  request.getParameter("period");
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");
        if( vtype == null||vtype=="" )
            vtype  =  "101";
        if("allday".equals(mode))
            mode=null;
        if("workday".equals(mode))
            mode="工作日";
        if("weekend".equals(mode))
            mode="非工作日";
        if("isweight".equals(weight))
            weight="weight_ext";
        if("notweight".equals(weight))
            weight="weight_org";
        Map<String,Object> map = new HashedMap();
        map.put("sdate", sdate);
        map.put("edate", edate);
        map.put("period", period);
        map.put("vtype", vtype);
        map.put("mode", mode);
        map.put("weight", weight);
        logger.info("Parameter for tripnum is \"{}\" and \"{}\" with period \"{}\" for vehicle type \"{}\"", sdate, edate, period, vtype);
        JSONObject jsonobject = new JSONObject();
        JSONArray dates = new JSONArray();//日期
        JSONArray avgTimes = new JSONArray();//车均
        JSONArray tripTimes = new JSONArray();//日总
        try {
            List<IntensionTripNum> content  =  this.rtmTripService.intensionTripNum(map);
            IntensionTripNum intension=content.get(content.size()-1);
            for (int i = 0; i <content.size() ; i++) {
                IntensionTripNum intensionTripNum=content.get(i);
                dates.add(i,intensionTripNum.getDates());
                Double avgTime=intensionTripNum.getVidnum()*100.0/intensionTripNum.getTripnum();
                avgTimes.add(i,String.format("%.2f",avgTime));
                tripTimes.add(intensionTripNum.getTripnum());
            }
            jsonobject.put("dates",dates);
            jsonobject.put("avgTimes",avgTimes);
            jsonobject.put("tripTimes",tripTimes);
            jsonobject.put("startdate",intension.getDates());
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //日均出行次数分布
    @ResponseBody
    @RequestMapping("/intensionTripNumDay.do")
    public void intensionTripNumDay(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");
        String period  =  request.getParameter("period");
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");
        if( vtype == null||vtype=="" )
            vtype  =  "101";
        if("allday".equals(mode))
            mode=null;
        if("workday".equals(mode))
            mode="工作日";
        if("weekend".equals(mode))
            mode="非工作日";
        if("isweight".equals(weight))
            weight="weight_ext";
        if("notweight".equals(weight))
            weight="weight_org";
        Map<String,Object> map = new HashedMap();
        map.put("sdate", sdate);
        map.put("edate", edate);
        map.put("period", period);
        map.put("vtype", vtype);
        map.put("mode", mode);
        map.put("weight", weight);
        logger.info("Parameter for tripnum is \"{}\" and \"{}\" with period \"{}\" for vehicle type \"{}\"", sdate, edate, period, vtype);
        JSONObject jsonobject = new JSONObject();
        JSONArray tripCnts = new JSONArray();//日期
        JSONArray vehicleCnts = new JSONArray();//车均
        JSONArray vehicleCntPercents = new JSONArray();//日总
        try {
            List<IntensionTripNum> content  =  this.rtmTripService.intensionTripNumDay(map);
            IntensionTripNum intension=content.get(content.size()-1);
            HashMap<Integer,Integer> mapnum=new HashMap();
            int max=0;
            long sum=0;
            for (int i = 0; i <content.size() ; i++) {
                IntensionTripNum intensionTripNum=content.get(i);
                int temp= (int) intensionTripNum.getTripnum();
                sum+=temp;
                if(max<intensionTripNum.getTripnum())
                 max=temp;
                if(mapnum.containsKey(temp)){
                  int maptemp= mapnum.get(temp);
                    mapnum.put(temp,maptemp+1);
                }else {
                    mapnum.put(temp,1);
                }
            }
            for (int i = 1; i <=max ; i++) {
                if(mapnum.containsKey(i)){
                    tripCnts.add(i-1,(i-1)+"-"+i+"次");
                    vehicleCnts.add(i-1,mapnum.get(i));
                    vehicleCntPercents.add(i-1,String.format("%.2f",mapnum.get(i)*100.0/sum));
                }else {
                    tripCnts.add(i-1,(i-1)+"-"+i+"次");
                    vehicleCnts.add(i-1,0);
                    vehicleCntPercents.add(i-1,0.0);
                }
            }
            jsonobject.put("tripCnts",tripCnts);
            jsonobject.put("vehicleCnts",vehicleCnts);
            jsonobject.put("vehicleCntPercents",vehicleCntPercents);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //日均出行次数分布
    @ResponseBody
    @RequestMapping("/intensionTripRate.do")
    public void intensionTripRate(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");
        String period  =  request.getParameter("period");
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");
        if( vtype == null||vtype=="" )
            vtype  =  "101";
        if("allday".equals(mode))
            mode=null;
        if("workday".equals(mode))
            mode="工作日";
        if("weekend".equals(mode))
            mode="非工作日";
        if("isweight".equals(weight))
            weight="weight_ext";
        if("notweight".equals(weight))
            weight="weight_org";
        Map<String,Object> map = new HashedMap();
        map.put("sdate", sdate);
        map.put("edate", edate);
        map.put("period", period);
        map.put("vtype", vtype);
        map.put("mode", mode);
        map.put("weight", weight);
        logger.info("Parameter for tripnum is \"{}\" and \"{}\" with period \"{}\" for vehicle type \"{}\"", sdate, edate, period, vtype);
        JSONObject jsonobject = new JSONObject();
        JSONArray dates = new JSONArray();
        JSONArray rates = new JSONArray();
        JSONArray mapData = new JSONArray();
        try {
            List<IntensionTripNum> content  =  this.rtmTripService.intensionTripRate(map);
            List<IntensionTripNum> max  =  this.rtmTripService.intensionTripRateMax(map);
            for (int i = 0; i <content.size() ; i++) {
                IntensionTripNum intensionTripNum=content.get(i);
                dates.add(i,intensionTripNum.getDates());
                double range= intensionTripNum.getVidnum()*100.0/max.get(0).getVidnum();
                rates.add(i,String.format("%.2f",range));
            }
            jsonobject.put("dates",dates);
            jsonobject.put("rates",rates);
            jsonobject.put("mapData",mapData);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
