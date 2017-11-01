package com.cn.hnust.controller;

import com.cn.hnust.pojo.DynamicAction;
import com.cn.hnust.pojo.DynamicActionTime;
import com.cn.hnust.pojo.StopCarType;
import com.cn.hnust.pojo.StopCarTypePart;
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
import java.util.*;

/**
 * 停车统计
 * Created by Administrator_xu on 2017/10/17.
 */
@Controller
public class StopCarController {
    private Logger logger  =  LoggerFactory.getLogger(RtmTripController.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sms = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat ymd2 = new SimpleDateFormat("yyyyMMdd");

    @Resource
    private IRtmTripService rtmTripService;
    //停车动态
    @ResponseBody
    @RequestMapping("/DynamicAction.do")
    public void dynamicAction(HttpServletRequest request, HttpServletResponse response) {
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
        //是否是工作日
        if("allday".equals(mode))
            mode=null;
        if("workday".equals(mode))
            mode="工作日";
        if("weekend".equals(mode))
            mode="非工作日";
        //是否扩样
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

        JSONArray hours = new JSONArray();//日期
        try {
            List<DynamicAction> content  =  this.rtmTripService.dynamicAction(map);
            HashMap<Integer,List<DynamicAction>> map_d= new HashMap<Integer, List<DynamicAction>>();
            for (int i = 0; i < content.size(); i++) {
                DynamicAction dynamicAction=content.get(i);
                int shutdown_hour=dynamicAction.getShutdown_hour();
                if(map_d.get(shutdown_hour)==null){
                    List<DynamicAction> list=new ArrayList<DynamicAction>();
                    list.add(dynamicAction);
                    map_d.put(shutdown_hour,list);
                }else {
                    map_d.get(shutdown_hour).add(dynamicAction);
                }
            }
            for (int i = 0; i <24 ; i++) {
                List<DynamicAction> list=map_d.get(i);
                JSONArray xy = new JSONArray();
                if(list==null){
                    hours.add(xy);
                    continue;
                }
                for (int j = 0; j <list.size() ; j++) {
                    JSONArray first_arry = new JSONArray();
                    DynamicAction dyn= list.get(j);
                    double x=dyn.getShutdown_x();
                    double y=dyn.getShutdown_y();
                    int triptype=dyn.getTriptype();
                    first_arry.add(x);
                    first_arry.add(y);
                    first_arry.add(triptype);
                    xy.add(first_arry);
                }
                hours.add(xy);
            }
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + hours.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //dynamicActionDaoTime//单次停车时长分布
    @ResponseBody
    @RequestMapping("PlugIn/Search/GetData/SEA00015")
    public void dynamicActionDaoTime(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");
        String period  =  request.getParameter("period");
        String part=request.getParameter("part");
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期
        String mode  =  request.getParameter("mode");
        //是否扩样
        String weight  =  request.getParameter("weight");
        //区域划分
        if(part.indexOf("100")!=-1)
            part="100";
        if( vtype == null||vtype=="" )
            vtype  =  "101";
        if("allday".equals(mode))
            mode=null;
        if("workday".equals(mode))
            mode="工作日";
        if("weekend".equals(mode))
            mode="非工作日";
        if("isweight".equals(weight)){
            weight="weight_ext";
        }else {
            weight="weight_org";
        }
        Map<String,Object> map = new HashedMap();
        map.put("sdate", sdate);
        map.put("edate", edate);
        map.put("period", period);
        map.put("vtype", vtype);
        map.put("mode", mode);
        map.put("weight", weight);
        map.put("part",part);
        JSONArray hours = new JSONArray();//日期
        try {
            List<DynamicActionTime> content  =  this.rtmTripService.dynamicActionDaoTime(map);
            HashMap<Integer,Long> map_d= new HashMap<Integer, Long>();
            int max=0;
            for (int i = 0; i < content.size(); i++) {
                DynamicActionTime dynamicAction=content.get(i);
                int shutdown_hour=dynamicAction.getShutdown_hour();
                if(shutdown_hour>max)
                    max=shutdown_hour;
                map_d.put(shutdown_hour,dynamicAction.getCountall());
            }
            for (int i = 0; i <= max ; i++) {
                Long list=map_d.get(i);

                if(list==null){
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("PrevStopTime",i);//n-carNum : 197962
                    jsonObject.put("carNum",0);
                    hours.add(jsonObject);
                }else {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("PrevStopTime",i);
                    jsonObject.put("carNum",list);
                    hours.add(jsonObject);
                }

            }
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + hours.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //dayAvgStopCar
    @ResponseBody
    @RequestMapping("/PlugIn/Search/GetData/SEA00016")
    public void dayAvgStopCarSEA00016(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate")+"";
        String edate  =  request.getParameter("edate")+"";
        String period  =  request.getParameter("period")+"";
        String part=request.getParameter("part")+"";
        if(part.indexOf("100")!=-1)
            part="100";
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
        if("isweight".equals(weight)){
            weight="weight_ext";
        }else {
            weight="weight_org";
        }
        Map<String,Object> map = new HashedMap();
        map.put("sdate", sdate);
        map.put("edate", edate);
        map.put("period", period);
        map.put("vtype", vtype);
        map.put("mode", mode);
        map.put("weight", weight);
        map.put("part",part);
        logger.info("Parameter for tripnum is \"{}\" and \"{}\" with period \"{}\" for vehicle type \"{}\"", sdate, edate, period, vtype);

        JSONArray hours = new JSONArray();//日期

        try {
            List<DynamicActionTime> content  =  this.rtmTripService.dayAvgStopCar(map);
            HashMap<Integer,Long> hash=new HashMap<Integer, Long>();
            for (int i = 0; i < content.size(); i++) {
                DynamicActionTime dynamicAction=content.get(i);
                int shutdown_hour=dynamicAction.getShutdown_hour();
                Long countall=dynamicAction.getCountall();
                hash.put(shutdown_hour,countall);

            }
            for (int i = 0; i < 24 ; i++) {
                if(hash.get(i)==null){
                    JSONObject jsonobject=new JSONObject();
                    jsonobject.put("avgCarNum",0);
                    jsonobject.put("avgStopTime",i);
                    hours.add(jsonobject);
                }else {
                JSONObject jsonobject=new JSONObject();
                jsonobject.put("avgCarNum",hash.get(i));
                jsonobject.put("avgStopTime",i);
                hours.add(jsonobject);
                }
            }
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + hours.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @ResponseBody
    @RequestMapping("/PlugIn/Search/GetData/SEA00017")
    public void dayAvgStopCarSEA00017(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");
        String period  =  request.getParameter("period");
        String part=request.getParameter("part")+"";

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
        if("isweight".equals(weight)){
            weight="weight_ext";
        }else {
            weight="weight_org";
        }
        Map<String,Object> map = new HashedMap();
        map.put("sdate", sdate);
        map.put("edate", edate);
        map.put("period", period);
        map.put("vtype", vtype);
        map.put("mode", mode);
        map.put("weight", weight);

        if(part.indexOf("100")!=-1)
            part="100";
        map.put("part",part);
        logger.info("Parameter for tripnum is \"{}\" and \"{}\" with period \"{}\" for vehicle type \"{}\"", sdate, edate, period, vtype);

        JSONArray hours = new JSONArray();//日期

        try {
            List<DynamicActionTime> content  =  this.rtmTripService.dayAvgStopCar(map);
            HashMap<Integer,Long> hash=new HashMap<Integer, Long>();
            long allnum=0;
            for (int i = 0; i < content.size(); i++) {
                DynamicActionTime dynamicAction=content.get(i);
                int shutdown_hour=dynamicAction.getShutdown_hour();
                Long countall=dynamicAction.getCountall();
                allnum+=countall;
            }
            JSONObject jsonobject=new JSONObject();
            jsonobject.put("totalStopNum",allnum);

            hours.add(jsonobject);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + hours.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //stopCarType
    @ResponseBody
    @RequestMapping("/PlugIn/Search/GetData/SEA00018")
    public void stopCarType(HttpServletRequest request, HttpServletResponse response) {
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
        if("isweight".equals(weight)){
            weight="weight_ext";
        }else {
            weight="weight_org";
        }
        Map<String,Object> map = new HashedMap();
        map.put("sdate", sdate);
        map.put("edate", edate);
        map.put("period", period);
        map.put("vtype", vtype);
        map.put("mode", mode);
        map.put("weight", weight);
        String part=request.getParameter("part")+"";
        if(part.indexOf("100")!=-1)
            part="100";
        map.put("part",part);
        logger.info("Parameter for tripnum is \"{}\" and \"{}\" with period \"{}\" for vehicle type \"{}\"", sdate, edate, period, vtype);

        JSONArray hours = new JSONArray();//日期

        try {
            List<StopCarType> content  =  this.rtmTripService.stopCarType(map);
            HashMap<Integer,Long> hash=new HashMap<Integer, Long>();
            long max=0;
            long linshi=0;//临时停车
            long rijian=0;//日间停车
            long yejian=0;//夜间停车
            long chaochang=0;//超长停车

            for (int i = 0; i < content.size(); i++) {
                StopCarType dynamicAction=content.get(i);
                int shutdown_hour=dynamicAction.getShutdown_hour();
                Long countall=dynamicAction.getCountall();
                int downhour=dynamicAction.getDownhour();
                if(shutdown_hour<3){
                    linshi+=countall;
                }
                if(3<=shutdown_hour&&shutdown_hour<=24){
                    if(6<=downhour&&downhour<18){
                        rijian+=countall;
                    }else {
                        yejian+=countall;
                    }
                }
                if(shutdown_hour>24){
                    chaochang+= countall;
                }
            }
            JSONObject linshi_stop = new JSONObject();//日期
            linshi_stop.put("CartypeName","临时停车");
            linshi_stop.put("typeCarNum",linshi);
            JSONObject rijian_stop = new JSONObject();//日期
            rijian_stop.put("CartypeName","日间停车");
            rijian_stop.put("typeCarNum",rijian);
            JSONObject yejian_stop = new JSONObject();//日期
            yejian_stop.put("CartypeName","夜间停车");
            yejian_stop.put("typeCarNum",yejian);
            JSONObject chaochang_stop = new JSONObject();//日期
            chaochang_stop.put("CartypeName","超长停车");
            chaochang_stop.put("typeCarNum",chaochang);
            hours.add(linshi_stop);
            hours.add(rijian_stop);
            hours.add(yejian_stop);
            hours.add(chaochang_stop);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + hours.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //StopIntension
    @ResponseBody
    @RequestMapping("/StopIntension.do")
    public void StopIntension(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        String sdate  =  request.getParameter("sdate");
        String edate  =  request.getParameter("edate");
        //小时，天，日，周参数
        String period  =  request.getParameter("period");
        //车辆类型
        String vtype  =  request.getParameter("vehicletype");
        //全部日期 节假日，工作日
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

        if("isweight".equals(weight)){
            weight="weight_ext";
        }else {
            weight="weight_org";
        }

        Map<String,Object> map = new HashedMap();
        map.put("sdate", sdate);
        map.put("edate", edate);
        map.put("period", period);
        map.put("vtype", vtype);
        map.put("mode", mode);
        map.put("weight", weight);

        String part=request.getParameter("part")+"";
        if(part.indexOf("100")!=-1)
            part="100";
        map.put("part",part);

        if("hourdstat".equals(period)){
            map.put("timetype", "hour");
        }
        if("daystat".equals(period)){
            map.put("timetype", "day");
        }
        if("weekstat".equals(period)){
            map.put("timetype", "DOW");
        }
        if("monthstat".equals(period)){
            map.put("timetype", "month");
        }

        JSONArray totaldates = new JSONArray();//日期
        JSONArray data = new JSONArray();//日期
        JSONObject hours=new JSONObject();
        JSONArray totalCarNum=new JSONArray();

        try {
            List<StopCarTypePart> content  =  this.rtmTripService.StopIntension(map);
                HashMap<Integer,List<StopCarTypePart>> partstop=new HashMap<Integer, List<StopCarTypePart>>();
                HashMap<String,Long> hash=new HashMap<String, Long>();
                HashMap<String,Long> out=new HashMap<String, Long>();
                if(!part.equals("100")){
                    //按区区分
                    for (int i = 0; i < content.size(); i++) {
                        StopCarTypePart dynamicAction=content.get(i);
                        int pa=dynamicAction.getPart();
                        if(partstop.get(pa)==null){
                            List<StopCarTypePart> li=new ArrayList<StopCarTypePart>();
                            li.add(dynamicAction);
                            partstop.put(pa,li);
                        }else{
                            partstop.get(pa).add(dynamicAction);
                        }
                    }

                    for (Map.Entry<Integer, List<StopCarTypePart>> m:partstop.entrySet()) {
                        int partkey=m.getKey();
                        List<StopCarTypePart> partvalue=m.getValue();
                        String name="";
                        if("hourdstat".equals(period)){
                            for (int i = 0; i < partvalue.size(); i++) {
                                StopCarTypePart dynamicAction=partvalue.get(i);
                                String shutdown_hour=dynamicAction.getDatetime();
                                name=dynamicAction.getName();
                                Long countall=dynamicAction.getCountall();
                                hash.put(shutdown_hour,countall);
                            }
                            JSONArray datapart = new JSONArray();//日期
                            for (int i = 0; i < 24 ; i++) {
                                totaldates.add(i);
                                if (hash.get(i+"")==null){
                                    datapart.add(0);
                                }else {
                                    datapart.add(hash.get(i+""));
                                }
                            }

                            JSONObject partobj= new JSONObject();//日期
                            partobj.put("AreaID",partkey);
                            partobj.put("barGap","10%");
                            partobj.put("barMaxWidth",20);//data
                            partobj.put("data",datapart);//data
                            partobj.put("name",name);//data
                            partobj.put("type","line");//data
                            totalCarNum.add(partobj);
                            hash.clear();
                        }
                        if("daystat".equals(period)||"monthstat".equals(period)){
                            JSONArray datapart = new JSONArray();//日期
                            for (int i = 0; i < partvalue.size(); i++) {
                                StopCarTypePart dynamicAction=partvalue.get(i);
                                String shutdown_hour=dynamicAction.getDatetime();
                                name=dynamicAction.getName();
                                Long countall=dynamicAction.getCountall();
                                totaldates.add(shutdown_hour);
                                datapart.add(countall);
                            }
                            JSONObject partobj= new JSONObject();//日期
                            partobj.put("AreaID",partkey);
                            partobj.put("barGap","10%");
                            partobj.put("barMaxWidth",20);//data
                            partobj.put("data",datapart);//data
                            partobj.put("name",name);//data
                            partobj.put("type","line");//data
                            totalCarNum.add(partobj);
                            hash.clear();
                        }
                        if("weekstat".equals(period)){
                            for (int i = 0; i < partvalue.size(); i++) {
                                StopCarTypePart dynamicAction=partvalue.get(i);
                                String shutdown_hour=dynamicAction.getDatetime();
                                name=dynamicAction.getName();
                                Long countall=dynamicAction.getCountall();
                                String week=getDayofweekorMonth(shutdown_hour,0);

                                if(out.get(week)==null){
                                    out.put(week,countall);
                                }else {
                                    long sum=out.get(week);
                                    out.put(week,(countall+sum));
                                }
                            }
                            ArrayList<Long> timeSeries = new ArrayList<Long>();
                            for (Map.Entry<String, Long> m2:out.entrySet()) {
                                timeSeries.add(ymd.parse(m2.getKey()).getTime());
                            }
                            Collections.sort(timeSeries);
                            JSONArray datapart = new JSONArray();//日期

                            for (Long ti:timeSeries) {
                                totaldates.add(ymd.format(ti));
                                datapart.add(out.get(ymd.format(ti)));
                            }
                            JSONObject partobj= new JSONObject();//日期
                            partobj.put("AreaID",partkey);
                            partobj.put("barGap","10%");
                            partobj.put("barMaxWidth",20);//data
                            partobj.put("data",datapart);//data
                            partobj.put("name",name);//data
                            partobj.put("type","line");//data
                            totalCarNum.add(partobj);
                            hash.clear();
                            out.clear();
                        }
                    }
                }else {
                    if("hourdstat".equals(period)){
                        for (int i = 0; i < content.size(); i++) {
                            StopCarTypePart dynamicAction=content.get(i);
                            String shutdown_hour=dynamicAction.getDatetime();
                            Long countall=dynamicAction.getCountall();
                            hash.put(shutdown_hour,countall);
                        }
                        for (int i = 0; i < 24 ; i++) {
                            totaldates.add(i);
                            if (hash.get(i+"")==null){
                                data.add(0);
                            }else {
                                data.add(hash.get(i+""));
                            }
                        }
                    }
                    if("daystat".equals(period)||"monthstat".equals(period)){
                        for (int i = 0; i < content.size(); i++) {
                            StopCarTypePart dynamicAction=content.get(i);
                            String shutdown_hour=dynamicAction.getDatetime();
                            Long countall=dynamicAction.getCountall();
                            totaldates.add(shutdown_hour);
                            data.add(countall);
                        }
                    }
                    if("weekstat".equals(period)){
                        for (int i = 0; i < content.size(); i++) {
                            StopCarTypePart dynamicAction=content.get(i);
                            String shutdown_hour=dynamicAction.getDatetime();
                            Long countall=dynamicAction.getCountall();
                            String week=getDayofweekorMonth(shutdown_hour,0);

                            if(out.get(week)==null){
                                out.put(week,countall);
                            }else {
                                long sum=out.get(week);
                                out.put(week,(countall+sum));
                            }
                        }
                        ArrayList<Long> timeSeries = new ArrayList<Long>();
                        for (Map.Entry<String, Long> m:out.entrySet()) {
                            timeSeries.add(ymd.parse(m.getKey()).getTime());
                        }
                        Collections.sort(timeSeries);
                        for (Long ti:timeSeries) {
                            totaldates.add(ymd.format(ti));
                            data.add(out.get(ymd.format(ti)));
                        }
                    }
                    JSONObject totalCarNumobj=new JSONObject();
                    totalCarNumobj.put("AreaID","6");
                    totalCarNumobj.put("barGap","10%");
                    totalCarNumobj.put("barMaxWidth",20);//data
                    totalCarNumobj.put("data",data);//data
                    totalCarNumobj.put("name","全市域");//data
                    totalCarNumobj.put("type","line");//data
                    totalCarNum.add(totalCarNumobj);
                }
            hours.put("totalCarNum",totalCarNum);
            hours.put("totaldates",totaldates);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + hours.toString() + ")");
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
}
