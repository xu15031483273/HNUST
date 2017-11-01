package com.cn.hnust.controller;

import com.cn.hnust.pojo.*;
import com.cn.hnust.service.IRtmTripService;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator_xu on 2017/10/12.
 */

@Controller
public class RtmTripController {

    private Logger logger  =  LoggerFactory.getLogger(RtmTripController.class);
    private final int distance=500;
    @Resource
    private IRtmTripService rtmTripService;

    //日均次均出行距离
    @ResponseBody
    @RequestMapping("/distanceTrip.do")
    public void getTripNum(HttpServletRequest request, HttpServletResponse response) {
        String period  =  request.getParameter("period");
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");;
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");

        //出行目的 0表示全部，1表示通勤
        String purpose  =  request.getParameter("purpose");
        if(purpose==null||purpose=="")
            purpose="0";

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

        String startpart=request.getParameter("startpart")+"";
        if(startpart.indexOf("100")!=-1||startpart=="null"||startpart.equals("null"))
            startpart="100";

        String endpart=request.getParameter("endpart")+"";
        if(endpart.indexOf("100")!=-1||endpart=="null"||endpart.equals("null"))
            endpart="100";

        String homepart=request.getParameter("homepart")+"";
        if(homepart.indexOf("100")!=-1||homepart==null||homepart.equals("null"))
            homepart="100";

        String workpart=request.getParameter("workpart")+"";
        if(workpart.indexOf("100")!=-1||workpart=="null"||workpart.equals("null"))
            workpart="100";
        HashMap<String,Object> mapparams=new HashMap<String, Object>();
        mapparams.put("sdate",sdate);
        mapparams.put("edate",edate);
        mapparams.put("vtype",vtype);
        mapparams.put("mode",mode);
        mapparams.put("weight",weight);
        mapparams.put("startpart",startpart);
        mapparams.put("endpart",endpart);
        mapparams.put("homepart",homepart);
        mapparams.put("workpart",workpart);
        mapparams.put("purpose",purpose);
        mapparams.put("distance",distance);
        mapparams.put("period",period);
        logger.info("Parameter for tripnum is \"{}\" and \"{}\" with period \"{}\" for vehicle type \"{}\"", sdate, edate, period, vtype);
        JSONObject jsonobject = new JSONObject();
        JSONArray dates = new JSONArray();
        JSONArray onceAvgDistance = new JSONArray();
        JSONArray carAvgDistance = new JSONArray();
        try {
            List<DateAndOnceAvgerDistance> content  =  this.rtmTripService.getDateAndOnceAvgerDistance(mapparams);
            List<DateInfo> dateInfo=this.rtmTripService.getDateInfo(mapparams);
            List<String> list_date=new ArrayList<String>();
            for (int i = 0; i <dateInfo.size() ; i++) {
                list_date.add(dateInfo.get(i).getDt());
            }
            for (int i = content.size()-1;  i >= 0 ; i--) {
                DateAndOnceAvgerDistance dateAndOnceAvgerDistance = content.get(i);
                if(list_date.contains(dateAndOnceAvgerDistance.getDate())){
                }else {
                    content.remove(i);
                }
            }
            if(period.equals("daystat")){
                for (int i  =  0; i <content.size() ; i++) {
                    DateAndOnceAvgerDistance dateAndOnceAvgerDistance = content.get(i);
                    dates.add(i,dateAndOnceAvgerDistance.getDate());
                    carAvgDistance.add(i,String.format("%.2f", dateAndOnceAvgerDistance.getDissum()*1.0/dateAndOnceAvgerDistance.getVidsum()/1000));
                    onceAvgDistance.add(i,String.format("%.2f",dateAndOnceAvgerDistance.getDissum()*1.0/dateAndOnceAvgerDistance.getTrip_count()/1000));
                }
            }
            if(period.equals("weekstat")){
                HashMap<String,List<DateAndOnceAvgerDistance>> map = new HashMap<String, List<DateAndOnceAvgerDistance>>();
                for (int i  =  content.size()-1; i >=0 ; i--) {
                    DateAndOnceAvgerDistance dateAndOnceAvgerDistance = content.get(i);
                    String day = dateAndOnceAvgerDistance.getDate();
                    String weekstart = getDayofweekorMonth(day,0);
                    if (map.containsKey(day)){
                        map.get(day).add(map.get(day).size(),dateAndOnceAvgerDistance);
                    }else {
                        List<DateAndOnceAvgerDistance> list = new ArrayList<DateAndOnceAvgerDistance>();
                        list.add(list.size(),dateAndOnceAvgerDistance);
                        map.put(weekstart,list);
                    }
                }
                Map<String, List<DateAndOnceAvgerDistance>> resultMap = sortMapByKey(map);    //按Key进行排序
                for (Map.Entry<String, List<DateAndOnceAvgerDistance>> m:resultMap.entrySet()) {
                    long dissum = 0;
                    int vidsum = 0;
                    int tripnum = 0;

                    String k = m.getKey();
                    List<DateAndOnceAvgerDistance> v = m.getValue();
                    dates.add(dates.size(),k);
                    for (int i  =  0; i <v.size() ; i++) {
                        dissum +=  v.get(i).getDissum();
                        vidsum += v.get(i).getVidsum();
                        tripnum += v.get(i).getTrip_count();
                    }
                    double carAvgDistanceNum = dissum*1.0/vidsum/1000;
                    double onceAvgDistanceNum = dissum*1.0/tripnum/1000;
                    carAvgDistance.add(carAvgDistance.size(),String.format("%.2f",carAvgDistanceNum));
                    onceAvgDistance.add(onceAvgDistance.size(),String.format("%.2f",onceAvgDistanceNum));
                }

            }
            if(period.equals("monthstat")){
                HashMap<String,List<DateAndOnceAvgerDistance>> map = new HashMap<String, List<DateAndOnceAvgerDistance>>();
                for (int i  =  0; i <content.size() ; i++) {
                    DateAndOnceAvgerDistance dateAndOnceAvgerDistance = content.get(i);
                    String day = dateAndOnceAvgerDistance.getDate();
                    String weekstart = getDayofweekorMonth(day,1);
                    if (map.containsKey(day)){
                        map.get(day).add(map.get(day).size(),dateAndOnceAvgerDistance);
                    }else {
                        List<DateAndOnceAvgerDistance> list = new ArrayList<DateAndOnceAvgerDistance>();
                        list.add(list.size(),dateAndOnceAvgerDistance);
                        map.put(weekstart,list);
                    }
                }
                Map<String, List<DateAndOnceAvgerDistance>> resultMap = sortMapByKey(map);
                for (Map.Entry<String, List<DateAndOnceAvgerDistance>> m:resultMap.entrySet()) {
                    long dissum = 0;
                    int vidsum = 0;
                    int tripnum = 0;

                    String k = m.getKey();
                    List<DateAndOnceAvgerDistance> v = m.getValue();
                    dates.add(dates.size(),k);
                    for (int i  =  0; i <v.size() ; i++) {
                        dissum +=  v.get(i).getDissum();
                        vidsum += v.get(i).getVidsum();
                        tripnum += v.get(i).getTrip_count();
                    }
                    double carAvgDistanceNum = dissum*1.0/vidsum/1000;
                    double onceAvgDistanceNum = dissum*1.0/tripnum/1000;
                    carAvgDistance.add(carAvgDistance.size(),String.format("%.2f",carAvgDistanceNum));
                    onceAvgDistance.add(onceAvgDistance.size(),String.format("%.2f",onceAvgDistanceNum));
                }

            }

            jsonobject.put("dates",dates);
            jsonobject.put("onceAvgDistance",onceAvgDistance);
            jsonobject.put("carAvgDistance",carAvgDistance);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //单次出行距离
    @ResponseBody
    @RequestMapping("/distanceTripDist.do")
    public void distanceTripDist(HttpServletRequest request, HttpServletResponse response) {
        String period  =  request.getParameter("period");
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");;
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");

        //出行目的 0表示全部，1表示通勤
        String purpose  =  request.getParameter("purpose");
        if(purpose==null||purpose=="")
            purpose="0";

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

        String startpart=request.getParameter("startpart")+"";
        if(startpart.indexOf("100")!=-1||startpart=="null"||startpart.equals("null"))
            startpart="100";

        String endpart=request.getParameter("endpart")+"";
        if(endpart.indexOf("100")!=-1||endpart=="null"||endpart.equals("null"))
            endpart="100";

        String homepart=request.getParameter("homepart")+"";
        if(homepart.indexOf("100")!=-1||homepart==null||homepart.equals("null"))
            homepart="100";

        String workpart=request.getParameter("workpart")+"";
        if(workpart.indexOf("100")!=-1||workpart=="null"||workpart.equals("null"))
            workpart="100";
        HashMap<String,Object> mapparams=new HashMap<String, Object>();
        mapparams.put("sdate",sdate);
        mapparams.put("edate",edate);
        mapparams.put("vtype",vtype);
        mapparams.put("mode",mode);
        mapparams.put("weight",weight);
        mapparams.put("startpart",startpart);
        mapparams.put("endpart",endpart);
        mapparams.put("homepart",homepart);
        mapparams.put("workpart",workpart);
        mapparams.put("purpose",purpose);
        mapparams.put("distance",distance);
        mapparams.put("period",period);
        logger.info("Parameter for travel distance distribution is \"{}\" and \"{}\" for vehicle type \"{}\"", sdate, edate, vtype);


        JSONObject jsonobject = new JSONObject();
        JSONArray distanceCnts = new JSONArray();
        JSONArray distances = new JSONArray();
        JSONArray percents = new JSONArray();
        try {
            List<DistanceTripDist> content  =  this.rtmTripService.getDistanceTripDist(mapparams);
            int sum = 0;
            for (int i  =  0; i < content.size(); i++) {
                DistanceTripDist distanceTripDist = content.get(i);
                sum+= distanceTripDist.getTrip_count();
            }
            for (int i  =  0; i <content.size() ; i++) {
                DistanceTripDist distanceTripDist = content.get(i);
                distanceCnts.add(i,i+"-"+(i+1)+"公里");
                distances.add(i,distanceTripDist.getTrip_count());
                percents.add(i,String.format("%.2f",distanceTripDist.getTrip_count()*1.0*100/sum));
            }
            jsonobject.put("distanceCnts",distanceCnts);
            jsonobject.put("distances",distances);
            jsonobject.put("percents",percents);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/daySumTripDistance.do")
    public void getdaySumTripDistance(HttpServletRequest request, HttpServletResponse response) {
        String period  =  request.getParameter("period");
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");;
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");

        //出行目的 0表示全部，1表示通勤
        String purpose  =  request.getParameter("purpose");
        if(purpose==null||purpose=="")
            purpose="0";

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

        String startpart=request.getParameter("startpart")+"";
        if(startpart.indexOf("100")!=-1||startpart=="null"||startpart.equals("null"))
            startpart="100";

        String endpart=request.getParameter("endpart")+"";
        if(endpart.indexOf("100")!=-1||endpart=="null"||endpart.equals("null"))
            endpart="100";

        String homepart=request.getParameter("homepart")+"";
        if(homepart.indexOf("100")!=-1||homepart==null||homepart.equals("null"))
            homepart="100";

        String workpart=request.getParameter("workpart")+"";
        if(workpart.indexOf("100")!=-1||workpart=="null"||workpart.equals("null"))
            workpart="100";
        HashMap<String,Object> mapparams=new HashMap<String, Object>();
        mapparams.put("sdate",sdate);
        mapparams.put("edate",edate);
        mapparams.put("vtype",vtype);
        mapparams.put("mode",mode);
        mapparams.put("weight",weight);
        mapparams.put("startpart",startpart);
        mapparams.put("endpart",endpart);
        mapparams.put("homepart",homepart);
        mapparams.put("workpart",workpart);
        mapparams.put("purpose",purpose);
        mapparams.put("distance",distance);
        mapparams.put("period",period);
        logger.info("Parameter for getDaySumTripDistance is \"{}\" and \"{}\" for vehicle type \"{}\"", sdate, edate, vtype);
        JSONObject jsonobject = new JSONObject();

        JSONArray distanceCnts = new JSONArray();
        JSONArray distances = new JSONArray();
        JSONArray percents = new JSONArray();
        try {
            List<DaySumVid> content  =  this.rtmTripService.getDaySumTripDistance(mapparams);
            HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
            int sum = content.size();
            for (int i  =  0; i <content.size() ; i++) {

                DaySumVid daySumVid = content.get(i);
                if(map.containsKey(daySumVid.getDissum())){
                    int mapvalue =  map.get(daySumVid.getDissum()) + 1;
                    map.put(daySumVid.getDissum(),mapvalue);
                }else {
                    map.put(daySumVid.getDissum(),1);
                }
            }
            int max=0;
            for (Map.Entry<Integer, Integer> m:map.entrySet()) {
                int k = m.getKey();
                int v = m.getValue();
                if(k<max){

                }else {
                    max=k;
                }
            }
            for (int i = 0; i <=max ; i++) {
               if(map.containsKey(i)){
                   int k = i;
                   int v=  map.get(i);
                   distanceCnts.add(k, k * 5 + "-" + (k+1)*5 + "公里" );
                   distances.add(k, v);
                   percents.add(k,String.format( "%.2f",v * 100.0 / (float)sum ) );
               } else {
                   int k = i;
                   int v=  0;
                   distanceCnts.add(k, k * 5 + "-" + (k+1)*5 + "公里" );
                   distances.add(k, v);
                   percents.add(k,String.format( "%.2f",v * 100.0 / (float)sum ) );
               }
            }
            jsonobject.put("distanceCnts",distanceCnts);
            jsonobject.put("distances",distances);
            jsonobject.put("percents",percents);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/disAvgConsume.do")
    public void disAvgConsume(HttpServletRequest request, HttpServletResponse response) {
        String period  =  request.getParameter("period");
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");;
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");

        //出行目的 0表示全部，1表示通勤
        String purpose  =  request.getParameter("purpose");
        if(purpose==null||purpose=="")
            purpose="0";

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

        String startpart=request.getParameter("startpart")+"";
        if(startpart.indexOf("100")!=-1||startpart=="null"||startpart.equals("null"))
            startpart="100";

        String endpart=request.getParameter("endpart")+"";
        if(endpart.indexOf("100")!=-1||endpart=="null"||endpart.equals("null"))
            endpart="100";

        String homepart=request.getParameter("homepart")+"";
        if(homepart.indexOf("100")!=-1||homepart==null||homepart.equals("null"))
            homepart="100";

        String workpart=request.getParameter("workpart")+"";
        if(workpart.indexOf("100")!=-1||workpart=="null"||workpart.equals("null"))
            workpart="100";
        HashMap<String,Object> mapparams=new HashMap<String, Object>();
        mapparams.put("sdate",sdate);
        mapparams.put("edate",edate);
        mapparams.put("vtype",vtype);
        mapparams.put("mode",mode);
        mapparams.put("weight",weight);
        mapparams.put("startpart",startpart);
        mapparams.put("endpart",endpart);
        mapparams.put("homepart",homepart);
        mapparams.put("workpart",workpart);
        mapparams.put("purpose",purpose);
        mapparams.put("distance",distance);
        mapparams.put("period",period);
        JSONObject jsonobject = new JSONObject();
        JSONArray hours = new JSONArray();
        JSONArray tripTimes = new JSONArray();
        try {
            List<DisAvgConsume> content  =  this.rtmTripService.getDisAvgConsume(mapparams);
            HashMap<Integer,DisAvgConsume> map=new HashMap<Integer, DisAvgConsume>();
            for (int i = 0; i <content.size() ; i++) {
                DisAvgConsume distanceTripDist = content.get(i);
                int n= Integer.parseInt(distanceTripDist.getHourofday());
                map.put(n,distanceTripDist);
            }
            for (int i = 0; i <24 ; i++) {
                if(map.containsKey(i)){
                    DisAvgConsume distanceTripDist = map.get(i);
                    double distanceInKm = distanceTripDist.getDistum() * 1.0 / 1000.0;
                    BigDecimal b = new BigDecimal(0.0);
                    if( distanceTripDist.getTrip_count() > 0 )
                        b  =  new BigDecimal( distanceInKm / (double)distanceTripDist.getTrip_count() );
                    double f1  =  b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    int hourStart = Integer.parseInt(distanceTripDist.getHourofday());
                    int hourEnd = Integer.parseInt(distanceTripDist.getHourofday()) + 1 ;
                    hours.add(i,String.format("%d时-%d时", hourStart, hourEnd));
                    tripTimes.add(i,String.valueOf(f1)) ;
                }else {
                    hours.add(i,i+"时-"+(i+1)+"时");
                    tripTimes.add(i,0) ;
                }
            }
            jsonobject.put("hours",hours);
            jsonobject.put("tripTimes",tripTimes);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/timeTripTime.do")
    public void timeTripTime(HttpServletRequest request, HttpServletResponse response) {
        String period  =  request.getParameter("period");
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");;
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");

        //出行目的 0表示全部，1表示通勤
        String purpose  =  request.getParameter("purpose");
        if(purpose==null||purpose=="")
            purpose="0";

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

        String startpart=request.getParameter("startpart")+"";
        if(startpart.indexOf("100")!=-1||startpart=="null"||startpart.equals("null"))
            startpart="100";

        String endpart=request.getParameter("endpart")+"";
        if(endpart.indexOf("100")!=-1||endpart=="null"||endpart.equals("null"))
            endpart="100";

        String homepart=request.getParameter("homepart")+"";
        if(homepart.indexOf("100")!=-1||homepart==null||homepart.equals("null"))
            homepart="100";

        String workpart=request.getParameter("workpart")+"";
        if(workpart.indexOf("100")!=-1||workpart=="null"||workpart.equals("null"))
            workpart="100";
        HashMap<String,Object> mapparams=new HashMap<String, Object>();
        mapparams.put("sdate",sdate);
        mapparams.put("edate",edate);
        mapparams.put("vtype",vtype);
        mapparams.put("mode",mode);
        mapparams.put("weight",weight);
        mapparams.put("startpart",startpart);
        mapparams.put("endpart",endpart);
        mapparams.put("homepart",homepart);
        mapparams.put("workpart",workpart);
        mapparams.put("purpose",purpose);
        mapparams.put("distance",distance);
        mapparams.put("period",period);
        logger.info("Parameter for tripnum is \"{}\" and \"{}\" with period \"{}\" for vehicle type \"{}\"", sdate, edate, period, vtype);

        JSONObject jsonobject = new JSONObject();
        JSONArray dates = new JSONArray();
        JSONArray onceAvgDistance = new JSONArray();
        JSONArray carAvgDistance = new JSONArray();
        try {
            List<TimeTripTime> content  =  this.rtmTripService.getTimeTripTime(mapparams);
            List<DateInfo> dateInfo=this.rtmTripService.getDateInfo(mapparams);
            List<String> list_date=new ArrayList<String>();
            for (int i = 0; i <dateInfo.size() ; i++) {
                list_date.add(dateInfo.get(i).getDt());
            }
            for (int i = content.size()-1;  i >= 0 ; i--) {
                TimeTripTime dateAndOnceAvgerDistance = content.get(i);
                if(list_date.contains(dateAndOnceAvgerDistance.getDate())){
                }else {
                    content.remove(i);
                }
            }
            if(period.equals("daystat")){
                for (int i  =  0; i <content.size() ; i++) {
                    TimeTripTime dateAndOnceAvgerDistance = content.get(i);
                    dates.add(i,dateAndOnceAvgerDistance.getDate());
                    carAvgDistance.add(i,String.format("%.2f", dateAndOnceAvgerDistance.getTimsum()*1.0/dateAndOnceAvgerDistance.getVidsum()/1000));
                    onceAvgDistance.add(i,String.format("%.2f",dateAndOnceAvgerDistance.getTimsum()*1.0/dateAndOnceAvgerDistance.getTrip_count()/1000));
                }
            }
            if(period.equals("weekstat")){
                HashMap<String,List<TimeTripTime>> map = new HashMap<String, List<TimeTripTime>>();
                for (int i  =  0; i <content.size() ; i++) {
                    TimeTripTime dateAndOnceAvgerDistance = content.get(i);
                    String day = dateAndOnceAvgerDistance.getDate();
                    String weekstart = getDayofweekorMonth(day,0);
                    if (map.containsKey(day)){
                        map.get(day).add(map.get(day).size(),dateAndOnceAvgerDistance);
                    }else {
                        List<TimeTripTime> list = new ArrayList<TimeTripTime>();
                        list.add(list.size(),dateAndOnceAvgerDistance);
                        map.put(weekstart,list);
                    }
                }
                Map<String, List<TimeTripTime>> resultMap = sortMapByKey2(map);    //按Key进行排序
                for (Map.Entry<String, List<TimeTripTime>> m:map.entrySet()) {
                    long dissum = 0;
                    int vidsum = 0;
                    int tripnum = 0;

                    String k = m.getKey();
                    List<TimeTripTime> v = m.getValue();
                    dates.add(dates.size(),k);
                    for (int i  =  0; i <v.size() ; i++) {
                        dissum+=  v.get(i).getTimsum();
                        vidsum+= v.get(i).getVidsum();
                        tripnum+= v.get(i).getTrip_count();
                    }
                    double carAvgDistanceNum = dissum*1.0/vidsum/1000;
                    double onceAvgDistanceNum = dissum*1.0/tripnum/1000;
                    carAvgDistance.add(carAvgDistance.size(),String.format("%.2f",carAvgDistanceNum));
                    onceAvgDistance.add(onceAvgDistance.size(),String.format("%.2f",onceAvgDistanceNum));
                }

            }
            if(period.equals("monthstat")){
                HashMap<String,List<TimeTripTime>> map = new HashMap<String, List<TimeTripTime>>();
                for (int i  =  0; i <content.size() ; i++) {
                    TimeTripTime dateAndOnceAvgerDistance = content.get(i);
                    String day = dateAndOnceAvgerDistance.getDate();
                    String weekstart = getDayofweekorMonth(day,1);
                    if (map.containsKey(day)){
                        map.get(day).add(map.get(day).size(),dateAndOnceAvgerDistance);
                    }else {
                        List<TimeTripTime> list = new ArrayList<TimeTripTime>();
                        list.add(list.size(),dateAndOnceAvgerDistance);
                        map.put(weekstart,list);
                    }
                }
                Map<String, List<TimeTripTime>> resultMap = sortMapByKey2(map);    //按Key进行排序
                for (Map.Entry<String, List<TimeTripTime>> m:map.entrySet()) {
                    long dissum = 0;
                    int vidsum = 0;
                    int tripnum = 0;

                    String k = m.getKey();
                    List<TimeTripTime> v = m.getValue();
                    dates.add(dates.size(),k);
                    for (int i  =  0; i <v.size() ; i++) {
                        dissum+=  v.get(i).getTimsum();
                        vidsum+= v.get(i).getVidsum();
                        tripnum+= v.get(i).getTrip_count();
                    }
                    double carAvgDistanceNum = dissum*1.0/vidsum/1000;
                    double onceAvgDistanceNum = dissum*1.0/tripnum/1000;
                    carAvgDistance.add(carAvgDistance.size(),String.format("%.2f",carAvgDistanceNum));
                    onceAvgDistance.add(onceAvgDistance.size(),String.format("%.2f",onceAvgDistanceNum));
                }

            }

            jsonobject.put("dates",dates);
            jsonobject.put("vehicleTripTimes",onceAvgDistance);
            jsonobject.put("onceTripTimes",carAvgDistance);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/timeConsumeDist.do")
    public void timeConsumeDist(HttpServletRequest request, HttpServletResponse response) {
        String period  =  request.getParameter("period");
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");;
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");

        //出行目的 0表示全部，1表示通勤
        String purpose  =  request.getParameter("purpose");
        if(purpose==null||purpose=="")
            purpose="0";

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

        String startpart=request.getParameter("startpart")+"";
        if(startpart.indexOf("100")!=-1||startpart=="null"||startpart.equals("null"))
            startpart="100";

        String endpart=request.getParameter("endpart")+"";
        if(endpart.indexOf("100")!=-1||endpart=="null"||endpart.equals("null"))
            endpart="100";

        String homepart=request.getParameter("homepart")+"";
        if(homepart.indexOf("100")!=-1||homepart==null||homepart.equals("null"))
            homepart="100";

        String workpart=request.getParameter("workpart")+"";
        if(workpart.indexOf("100")!=-1||workpart=="null"||workpart.equals("null"))
            workpart="100";
        HashMap<String,Object> mapparams=new HashMap<String, Object>();
        mapparams.put("sdate",sdate);
        mapparams.put("edate",edate);
        mapparams.put("vtype",vtype);
        mapparams.put("mode",mode);
        mapparams.put("weight",weight);
        mapparams.put("startpart",startpart);
        mapparams.put("endpart",endpart);
        mapparams.put("homepart",homepart);
        mapparams.put("workpart",workpart);
        mapparams.put("purpose",purpose);
        mapparams.put("distance",distance);
        mapparams.put("period",period);
        logger.info("Parameter for travel distance distribution is \"{}\" and \"{}\" for vehicle type \"{}\"", sdate, edate, vtype);


        JSONObject jsonobject = new JSONObject();
        JSONArray distanceCnts = new JSONArray();
        JSONArray distances = new JSONArray();
        JSONArray percents = new JSONArray();
        try {
            List<DistanceTripDist> content  =  this.rtmTripService.getTimeConsumeDist(mapparams);
            int sum = 0;
            for (int i  =  0; i < content.size(); i++) {
                DistanceTripDist distanceTripDist = content.get(i);
                sum+= distanceTripDist.getTrip_count();
            }
            for (int i  =  0; i <content.size() ; i++) {
                DistanceTripDist distanceTripDist = content.get(i);
                BigDecimal b  =  new BigDecimal(distanceTripDist.getTrip_count() * 1.0 * 100.0 / sum);
                double f1  =  b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                distanceCnts.add(i,i*5+"-"+(i+1)*5+"分钟");
                distances.add(i,distanceTripDist.getTrip_count());
                percents.add(i,f1);
            }
            List<JSONArray> perc=new ArrayList<JSONArray>();
            jsonobject.put("dates",perc);
            jsonobject.put("distanceCnts",distanceCnts);
            jsonobject.put("travalNum",distances);
            jsonobject.put("percents",percents);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //getAvgCarConsume
    @ResponseBody
    @RequestMapping("/avgCarConsume.do")
    public void avgCarConsume(HttpServletRequest request, HttpServletResponse response) {
        String period  =  request.getParameter("period");
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");;
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");

        //出行目的 0表示全部，1表示通勤
        String purpose  =  request.getParameter("purpose");
        if(purpose==null||purpose=="")
            purpose="0";

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

        String startpart=request.getParameter("startpart")+"";
        if(startpart.indexOf("100")!=-1||startpart=="null"||startpart.equals("null"))
            startpart="100";

        String endpart=request.getParameter("endpart")+"";
        if(endpart.indexOf("100")!=-1||endpart=="null"||endpart.equals("null"))
            endpart="100";

        String homepart=request.getParameter("homepart")+"";
        if(homepart.indexOf("100")!=-1||homepart==null||homepart.equals("null"))
            homepart="100";

        String workpart=request.getParameter("workpart")+"";
        if(workpart.indexOf("100")!=-1||workpart=="null"||workpart.equals("null"))
            workpart="100";
        HashMap<String,Object> mapparams=new HashMap<String, Object>();
        mapparams.put("sdate",sdate);
        mapparams.put("edate",edate);
        mapparams.put("vtype",vtype);
        mapparams.put("mode",mode);
        mapparams.put("weight",weight);
        mapparams.put("startpart",startpart);
        mapparams.put("endpart",endpart);
        mapparams.put("homepart",homepart);
        mapparams.put("workpart",workpart);
        mapparams.put("purpose",purpose);
        mapparams.put("distance",distance);
        mapparams.put("period",period);
        logger.info("Parameter for getDaySumTripDistance is \"{}\" and \"{}\" for vehicle type \"{}\"", sdate, edate, vtype);


        JSONObject jsonobject = new JSONObject();
        JSONArray distanceCnts = new JSONArray();
        JSONArray distances = new JSONArray();
        JSONArray percents = new JSONArray();
        try {
            List<DaySumVid> content  =  this.rtmTripService.getDaySumTripDistance(mapparams);
            Map<Integer,Integer> map = new TreeMap<Integer, Integer>();
            int sum = content.size();
            for (int i  =  0; i <content.size() ; i++) {
                DaySumVid daySumVid = content.get(i);
                if(map.containsKey(daySumVid.getDissum())){
                    int mapvalue =  map.get(daySumVid.getDissum()) + 1;
                    map.put(daySumVid.getDissum(),mapvalue);
                }else {
                    map.put(daySumVid.getDissum(),1);
                }
            }
            int max=0;
            for (Map.Entry<Integer, Integer> m:map.entrySet()) {
                int k = m.getKey();
                int v = m.getValue();
                if(k<max){

                }else {
                    max=k;
                }
            }
            for (int i = 0; i <=max ; i++) {
                distanceCnts.add(i,i*5+"-"+(i+1)*5+"分钟");
                if(map.containsKey(i)){
                    distances.add(i,map.get(i)+"");
                    percents.add(i,String.format("%.2f",map.get(i) * 1.0 * 100.0 / sum));
                }else {
                    distances.add(i, String.valueOf(0));
                    percents.add(i, String.valueOf(0));
                }
            }
            jsonobject.put("dates","");
            jsonobject.put("distanceCnts",distanceCnts);
            jsonobject.put("distanceCarNum",distances);
            jsonobject.put("percents",percents);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/timeAvgConsume.do")
    public void timeAvgConsume(HttpServletRequest request, HttpServletResponse response) {
        String period  =  request.getParameter("period");
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");;
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");

        //出行目的 0表示全部，1表示通勤
        String purpose  =  request.getParameter("purpose");
        if(purpose==null||purpose=="")
            purpose="0";

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

        String startpart=request.getParameter("startpart")+"";
        if(startpart.indexOf("100")!=-1||startpart=="null"||startpart.equals("null"))
            startpart="100";

        String endpart=request.getParameter("endpart")+"";
        if(endpart.indexOf("100")!=-1||endpart=="null"||endpart.equals("null"))
            endpart="100";

        String homepart=request.getParameter("homepart")+"";
        if(homepart.indexOf("100")!=-1||homepart==null||homepart.equals("null"))
            homepart="100";

        String workpart=request.getParameter("workpart")+"";
        if(workpart.indexOf("100")!=-1||workpart=="null"||workpart.equals("null"))
            workpart="100";
        HashMap<String,Object> mapparams=new HashMap<String, Object>();
        mapparams.put("sdate",sdate);
        mapparams.put("edate",edate);
        mapparams.put("vtype",vtype);
        mapparams.put("mode",mode);
        mapparams.put("weight",weight);
        mapparams.put("startpart",startpart);
        mapparams.put("endpart",endpart);
        mapparams.put("homepart",homepart);
        mapparams.put("workpart",workpart);
        mapparams.put("purpose",purpose);
        mapparams.put("distance",distance);
        mapparams.put("period",period);
        JSONObject jsonobject = new JSONObject();
        JSONArray hours = new JSONArray();
        JSONArray tripTimes = new JSONArray();
        try {
            List<DisAvgConsume> content  =  this.rtmTripService.timeAvgConsume(mapparams);
            HashMap<Integer,DisAvgConsume> map=new HashMap<Integer, DisAvgConsume>();
            for (int i = 0; i <content.size() ; i++) {
                DisAvgConsume distanceTripDist = content.get(i);
                int n= Integer.parseInt(distanceTripDist.getHourofday());
                map.put(n,distanceTripDist);
            }
            for (int i = 0; i <24 ; i++) {
                if(map.containsKey(i)){
                    DisAvgConsume distanceTripDist = map.get(i);
                    double distanceInKm = distanceTripDist.getDistum() * 1.0 / 60.0;
                    BigDecimal b = new BigDecimal(0.0);
                    if( distanceTripDist.getTrip_count() > 0 )
                        b  =  new BigDecimal( distanceInKm / (double)distanceTripDist.getTrip_count() );
                    double f1  =  b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    int hourStart = Integer.parseInt(distanceTripDist.getHourofday());
                    int hourEnd = Integer.parseInt(distanceTripDist.getHourofday()) + 1 ;
                    hours.add(i,String.format("%d时-%d时", hourStart, hourEnd));
                    tripTimes.add(i,String.valueOf(f1)) ;
                }else {
                    hours.add(i,i+"时-"+(i+1)+"时");
                    tripTimes.add(i,0) ;
                }
            }
            jsonobject.put("hours",hours);
            jsonobject.put("tripTimes",tripTimes);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //出行速度
    @ResponseBody
    @RequestMapping("/speedWithTriplen.do")
    public void speedWithTriplen(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");;
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");

        //出行目的 0表示全部，1表示通勤
        String purpose  =  request.getParameter("purpose");
        if(purpose==null||purpose=="")
            purpose="0";

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

        String startpart=request.getParameter("startpart")+"";
        if(startpart.indexOf("100")!=-1||startpart=="null"||startpart.equals("null"))
            startpart="100";

        String endpart=request.getParameter("endpart")+"";
        if(endpart.indexOf("100")!=-1||endpart=="null"||endpart.equals("null"))
            endpart="100";

        String homepart=request.getParameter("homepart")+"";
        if(homepart.indexOf("100")!=-1||homepart==null||homepart.equals("null"))
            homepart="100";

        String workpart=request.getParameter("workpart")+"";
        if(workpart.indexOf("100")!=-1||workpart=="null"||workpart.equals("null"))
            workpart="100";
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("sdate",sdate);
        map.put("edate",edate);
        map.put("vtype",vtype);
        map.put("mode",mode);
        map.put("weight",weight);
        map.put("startpart",startpart);
        map.put("endpart",endpart);
        map.put("homepart",homepart);
        map.put("workpart",workpart);
        map.put("purpose",purpose);
        map.put("distance",distance);
        JSONObject jsonobject = new JSONObject();
        JSONArray jsonarray = new JSONArray();
        try {
            List<SpeedWithTriplen> content  =  this.rtmTripService.speedWithTriplen(map);
            for (int i = 0; i < content.size() ; i++) {
                JSONArray speedAndDistance = new JSONArray();
                SpeedWithTriplen speedWithTriplen=content.get(i);
                speedAndDistance.add(0,String.format("%.2f",speedWithTriplen.getTriplen()/1000));
                speedAndDistance.add(1,String.format("%.2f",speedWithTriplen.getSpeed()));
                jsonarray.add(jsonarray.size(),speedAndDistance);
            }
            jsonobject.put("data",jsonarray);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //speedWithTripTime
    @ResponseBody
    @RequestMapping("/speedWithTripTime.do")
    public void speedWithTripTime(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");;
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");

        //出行目的 0表示全部，1表示通勤
        String purpose  =  request.getParameter("purpose");
        if(purpose==null||purpose=="")
            purpose="0";

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

        String startpart=request.getParameter("startpart")+"";
        if(startpart.indexOf("100")!=-1||startpart=="null"||startpart.equals("null"))
            startpart="100";

        String endpart=request.getParameter("endpart")+"";
        if(endpart.indexOf("100")!=-1||endpart=="null"||endpart.equals("null"))
            endpart="100";

        String homepart=request.getParameter("homepart")+"";
        if(homepart.indexOf("100")!=-1||homepart==null||homepart.equals("null"))
            homepart="100";

        String workpart=request.getParameter("workpart")+"";
        if(workpart.indexOf("100")!=-1||workpart=="null"||workpart.equals("null"))
            workpart="100";
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("sdate",sdate);
        map.put("edate",edate);
        map.put("vtype",vtype);
        map.put("mode",mode);
        map.put("weight",weight);
        map.put("startpart",startpart);
        map.put("endpart",endpart);
        map.put("homepart",homepart);
        map.put("workpart",workpart);
        map.put("purpose",purpose);
        map.put("distance",distance);
        JSONObject jsonobject = new JSONObject();
        JSONArray jsonarray = new JSONArray();
        try {
            List<SpeedWithTriplen> content  =  this.rtmTripService.speedWithTripTime(map);
            for (int i = 0; i < content.size() ; i++) {
                JSONArray speedAndDistance = new JSONArray();
                SpeedWithTriplen speedWithTriplen=content.get(i);
                speedAndDistance.add(0,String.format("%.2f",speedWithTriplen.getTriplen()/60));
                speedAndDistance.add(1,String.format("%.2f",speedWithTriplen.getSpeed()));
                jsonarray.add(jsonarray.size(),speedAndDistance);
            }
            jsonobject.put("data",jsonarray);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDayofweekorMonth(String date,int i){
        String str  =  date;
        SimpleDateFormat sdf  =  new SimpleDateFormat("yyyy-MM-dd");
        Date date1  =  null;
        try {
            date1  =  sdf.parse(str);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Calendar cal  =  Calendar.getInstance();
        cal.setTime(date1);

        if(i==0){
            cal.set(Calendar.DAY_OF_WEEK, 1);
            System.out.println("本周开始日期："+sdf.format(cal.getTime()));
            return  sdf.format(cal.getTime())+"";
        }else if(i==1){
            cal.set(Calendar.DAY_OF_MONTH, 1);
            System.out.println("本月开始日期："+sdf.format(cal.getTime()));
            return sdf.format(cal.getTime())+"";
        }else {
            return "日期类型错误";
        }
    }

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, List<DateAndOnceAvgerDistance>> sortMapByKey(HashMap<String, List<DateAndOnceAvgerDistance>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, List<DateAndOnceAvgerDistance>> sortMap = new TreeMap<String, List<DateAndOnceAvgerDistance>>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }
    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, List<TimeTripTime>> sortMapByKey2(HashMap<String, List<TimeTripTime>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, List<TimeTripTime>> sortMap = new TreeMap<String, List<TimeTripTime>>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

}
class MapKeyComparator implements Comparator<String>{

    @Override
    public int compare(String str1, String str2) {

        return str1.compareTo(str2);
    }
}
