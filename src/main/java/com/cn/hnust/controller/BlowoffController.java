package com.cn.hnust.controller;

import com.cn.hnust.pojo.Blowoff;
import com.cn.hnust.pojo.DynamicAction;
import com.cn.hnust.pojo.MapJson;
import com.cn.hnust.service.BlowoffService;
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
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator_xu on 2017/10/24.
 */
@Controller
public class BlowoffController {
    private Logger logger  =  LoggerFactory.getLogger(RtmTripController.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sms = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat ymd2 = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat hh= new SimpleDateFormat("HH");
    private static SimpleDateFormat mm= new SimpleDateFormat("mm");
    @Resource
    private BlowoffService blowoffService;
    //车均日总出行
    @ResponseBody
    @RequestMapping("/data/RecentDischargeWithIndexList")
    public void dynamicAction(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        JSONArray hours = new JSONArray();//日期
        int[] num=new int[24*4];
        Date ds=new Date();
        Date yesterday = new Date(ds.getTime() - 86400000L);

        String date=ymd.format(ds);
        String yester=ymd.format(yesterday);
        int h=Integer.parseInt(hh.format(ds));
        int m=Integer.parseInt(mm.format(ds));
        int now=h*60+m;
        JSONArray jsonArray=new JSONArray();


        try {
            List<Blowoff> content  =  this.blowoffService.getRtmPoiById();
            for (int i = 0; i < content.size() ; i++) {
                Blowoff blowoff=content.get(i);
                String firstTime=blowoff.getFirstTime();
                int minute=Integer.parseInt(blowoff.getMinutes());
                int hour=Integer.parseInt(blowoff.getHours());
                num[(hour*60+minute)/15]++;
            }
            for (int i = 0; i < num.length ; i++) {
                JSONObject jsonobject=new JSONObject();
                jsonobject.put("api",num[i]/10);
                jsonobject.put("apilv",1);
                jsonobject.put("co",String.format("%.2f",num[i]*10.1));
                jsonobject.put("co2",String.format("%.2f",num[i]*300.2));
                jsonobject.put("co2_max",String.format("%.2f",num[i]*320.3));
                jsonobject.put("co2_min",String.format("%.2f",num[i]*200.2));
                jsonobject.put("co_max",String.format("%.2f",num[i]*11.3));
                jsonobject.put("co_min",String.format("%.2f",num[i]*5.2));
                if(now<num[i]){
                    if(i*15/60<10){
                        if(i*15%60<10){
                            jsonobject.put("dtime",date+" 0"+i*15/60+":0"+i*15%60+":00");
                        }else {
                            jsonobject.put("dtime",date+" 0"+i*15/60+":"+i*15%60+":00");
                        }
                    }else {
                        if(i*15%60<10){
                            jsonobject.put("dtime",date+" "+i*15/60+":0"+i*15%60+":00");
                        }else {
                            jsonobject.put("dtime",date+" "+i*15/60+":"+i*15%60+":00");
                        }
                    }

                }else {
                    if(i*15/60<10){
                        if(i*15%60<10){
                            jsonobject.put("dtime",yester+" 0"+i*15/60+":0"+i*15%60+":00");
                        }else {
                            jsonobject.put("dtime",yester+" 0"+i*15/60+":"+i*15%60+":00");
                        }
                    }else {
                        if(i*15%60<10){
                            jsonobject.put("dtime",yester+" "+i*15/60+":0"+i*15%60+":00");
                        }else {
                            jsonobject.put("dtime",yester+" "+i*15/60+":"+i*15%60+":00");
                        }
                    }
                }
                jsonobject.put("hc",String.format("%.2f",num[i]*1.1));
                jsonobject.put("hc_max",String.format("%.2f",num[i]*1.2));
                jsonobject.put("hc_min",String.format("%.2f",num[i]*1.1));
                jsonobject.put("idx",String.format("%.2f",num[i]*1.1/60));
                jsonobject.put("nox",String.format("%.2f",num[i]*2.1));
                jsonobject.put("nox_max",String.format("%.2f",num[i]*2.4));
                jsonobject.put("nox_min",String.format("%.2f",num[i]*1.2));
                jsonobject.put("oil",String.format("%.2f",num[i]*100.2));
                jsonobject.put("oil_max",String.format("%.2f",num[i]*120.2));
                jsonobject.put("oil_min",String.format("%.2f",num[i]*80.0));
                jsonobject.put("pm",String.format("%.2f",num[i]*1.1/11));
                jsonobject.put("pm_max",String.format("%.2f",num[i]*1.1/11));
                jsonobject.put("pm_min",String.format("%.2f",num[i]*1.1/22));

                jsonArray.add(jsonobject);
            }
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonArray.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //车均日总出行
    @ResponseBody
    @RequestMapping("/data/PlayDTimeList")
    public void PlayDTimeList(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        JSONArray hours = new JSONArray();//日期
        int[] num=new int[24*4];
        Date ds=new Date();
        Date yesterday = new Date(ds.getTime() - 86400000L);

        String date=ymd.format(ds);
        String yester=ymd.format(yesterday);
        int h=Integer.parseInt(hh.format(ds));
        int m=Integer.parseInt(mm.format(ds));
        int now=h*60+m;
        JSONArray jsonArray=new JSONArray();

        try {
            List<Blowoff> content  =  this.blowoffService.getRtmPoiById();
            for (int i = 0; i < content.size() ; i++) {
                Blowoff blowoff=content.get(i);
                String firstTime=blowoff.getFirstTime();
                int minute=Integer.parseInt(blowoff.getMinutes());
                int hour=Integer.parseInt(blowoff.getHours());
                num[(hour*60+minute)/15]++;
            }
            for (int i = 0; i < num.length ; i++) {
                if(now<num[i]){
                    if(i*15/60<10){
                        if(i*15%60<10){
                            jsonArray.add(date+" 0"+i*15/60+":0"+i*15%60+":00");
                        }else {
                            jsonArray.add(date+" 0"+i*15/60+":"+i*15%60+":00");
                        }
                    }else {
                        if(i*15%60<10){
                            jsonArray.add(date+" "+i*15/60+":0"+i*15%60+":00");
                        }else {
                            jsonArray.add(date+" "+i*15/60+":"+i*15%60+":00");
                        }
                    }

                }else {
                    if(i*15/60<10){
                        if(i*15%60<10){
                            jsonArray.add(yester+" 0"+i*15/60+":0"+i*15%60+":00");
                        }else {
                            jsonArray.add(yester+" 0"+i*15/60+":"+i*15%60+":00");
                        }
                    }else {
                        if(i*15%60<10){
                            jsonArray.add(yester+" "+i*15/60+":0"+i*15%60+":00");
                        }else {
                            jsonArray.add(yester+" "+i*15/60+":"+i*15%60+":00");
                        }
                    }
                }
            }
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonArray.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/data/TrafficCongestion")
    public void TrafficCongestion(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        JSONArray hours = new JSONArray();//日期
        int[] num=new int[24*4];
        Date ds=new Date();
        Date yesterday = new Date(ds.getTime() - 86400000L);

        String date=ymd.format(ds);
        String yester=ymd.format(yesterday);
        int h=Integer.parseInt(hh.format(ds));
        int m=Integer.parseInt(mm.format(ds));
        int now=h*60+m;
        try {
            List<Blowoff> content  =  this.blowoffService.getRtmPoiById();
            int max=0;
            for (int i = 0; i < content.size() ; i++) {
                Blowoff blowoff=content.get(i);
                String firstTime=blowoff.getFirstTime();
                int minute=Integer.parseInt(blowoff.getMinutes());
                int hour=Integer.parseInt(blowoff.getHours());
                num[(hour*60+minute)/15]++;
            }
            for (int i = 0; i < num.length ; i++) {
                if(max<num[i]){
                    max=num[i];
                }
            }
            JSONObject jsobj=new JSONObject();
            int nowdata=num[(h*60+m)/15];
            double nao=nowdata*10.0/nowdata/1.4;
            String doudata=String.format("%.2f",nao);
            String color="";
            String name="";
            if(nao>7){
                color="#B22222";
                name="重度拥堵";
            }
            if(3 <= nao && nao <= 7){
                color="#FFA500";
                name="中度拥堵";
            }
            if(nao<3){
                color="#F0FFF0";
                name="轻度拥堵";
            }
            jsobj.put("color",color);//#B22222//#FFA500//#F0FFF0
            jsobj.put("dtime",sdf.format(new Date(ds.getTime() - 900000L)));
            jsobj.put("idx",doudata);
            jsobj.put("intime",sdf.format(ds));
            jsobj.put("lvl","中度拥堵");//轻度拥堵//中度拥堵//重度拥堵
            jsobj.put("speed",String.format("%.2f",nao*3));
            jsobj.put("zone","全路网");

            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsobj.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ListPaifRing
    @ResponseBody
    @RequestMapping("/data/ListPaifRing")
    public void ListPaifRing(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        JSONArray hours = new JSONArray();//日期
        int[] num=new int[24*4];
        Date ds=new Date();
        Date yesterday = new Date(ds.getTime() - 86400000L);

        String date=ymd.format(ds);
        String yester=ymd.format(yesterday);
        int h=Integer.parseInt(hh.format(ds));
        int m=Integer.parseInt(mm.format(ds));
        int now=h*60+m;
        JSONArray jsonArray=new JSONArray();
        try {
            List<Blowoff> content  =  this.blowoffService.getRtmPoiById();
            int max=0;
            for (int i = 0; i < content.size() ; i++) {
                Blowoff blowoff=content.get(i);
                String firstTime=blowoff.getFirstTime();
                int minute=Integer.parseInt(blowoff.getMinutes());
                int hour=Integer.parseInt(blowoff.getHours());
                num[(hour*60+minute)/15]++;
            }
            for (int i = 0; i < num.length ; i++) {
                if(max<num[i]){
                    max=num[i];
                }
            }
            JSONObject jsobj=new JSONObject();
            jsobj.put("area","66.44");
            jsobj.put("co",String.format("%.2f",num[(h*60+m)/15]*1.1));
            jsobj.put("co2",String.format("%.2f",num[(h*60+m)/15]*1000.1));
            jsobj.put("co2_lv",2);
            jsobj.put("co_lv",3);
            jsobj.put("hc",String.format("%.2f",num[(h*60+m)/15]/15.1));
            jsobj.put("hc_lv",2);
            jsobj.put("id","1");
            jsobj.put("name","二环内");
            jsobj.put("nox",String.format("%.2f",num[(h*60+m)/15]/3.1));
            jsobj.put("nox_lv",3);
            jsobj.put("oil",String.format("%.2f",num[(h*60+m)/15]*28.1));
            jsobj.put("oil_lv",3);
            jsobj.put("pm",String.format("%.2f",num[(h*60+m)/15]/70.1));
            jsobj.put("pm_lv",2);
            jsonArray.add(jsobj);
            JSONObject jsobj2=new JSONObject();
            jsobj2.put("area","101.63");
            jsobj2.put("co",String.format("%.2f",num[(h*60+m)/15]*2.1));
            jsobj2.put("co2",String.format("%.2f",num[(h*60+m)/15]*1300.1));
            jsobj2.put("co2_lv",3);
            jsobj2.put("co_lv",4);
            jsobj2.put("hc",String.format("%.2f",num[(h*60+m)/15]/10.1));
            jsobj2.put("hc_lv",54);
            jsobj2.put("id","2");
            jsobj2.put("name","二环至三环");
            jsobj2.put("nox",String.format("%.2f",num[(h*60+m)/15]/2.1));
            jsobj2.put("nox_lv",3);
            jsobj2.put("oil",String.format("%.2f",num[(h*60+m)/15]*40.1));
            jsobj2.put("oil_lv",4);
            jsobj2.put("pm",String.format("%.2f",num[(h*60+m)/15]/60.1));
            jsobj2.put("pm_lv",4);
            jsonArray.add(jsobj2);
            JSONObject jsobj3=new JSONObject();
            jsobj3.put("area","150.91");
            jsobj3.put("co",String.format("%.2f",num[(h*60+m)/15]*3.1));
            jsobj3.put("co2",String.format("%.2f",num[(h*60+m)/15]*1500.1));
            jsobj3.put("co2_lv",3);
            jsobj3.put("co_lv",4);
            jsobj3.put("hc",String.format("%.2f",num[(h*60+m)/15]/12.1));
            jsobj3.put("hc_lv",4);
            jsobj3.put("id","3");
            jsobj3.put("name","三环至四环");
            jsobj3.put("nox",String.format("%.2f",num[(h*60+m)/15]/4.1));
            jsobj3.put("nox_lv",4);
            jsobj3.put("oil",String.format("%.2f",num[(h*60+m)/15]*35.1));
            jsobj3.put("oil_lv",3);
            jsobj3.put("pm",String.format("%.2f",num[(h*60+m)/15]/54.1));
            jsobj3.put("pm_lv",4);
            jsonArray.add(jsobj3);

            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsonArray.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/data/WeatherReal")
    public void WeatherReal(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        JSONArray hours = new JSONArray();//日期
        int[] num=new int[24*4];
        Date ds=new Date();
        Date yesterday = new Date(ds.getTime() - 86400000L);

        String date=ymd.format(ds);
        String yester=ymd.format(yesterday);
        int h=Integer.parseInt(hh.format(ds));
        int m=Integer.parseInt(mm.format(ds));
        int now=h*60+m;
        JSONArray jsonArray=new JSONArray();
        try {
            JSONObject jsobj=new JSONObject();
            jsobj.put("dtime",date);
            jsobj.put("fl",3);
            jsobj.put("fx","西南风");
            jsobj.put("jsl","0");
            jsobj.put("kqzl","37");
            jsobj.put("sd","22");
            jsobj.put("wd",14);

            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + jsobj.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/data/LatesDTime")
    public void LatesDTime(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        JSONArray hours = new JSONArray();//日期
        int[] num=new int[24*4];
        Date ds=new Date();
        Date yesterday = new Date(ds.getTime() - 86400000L);

        String date=ymd.format(ds);
        String yester=ymd.format(yesterday);
        int h=Integer.parseInt(hh.format(ds));
        int m=Integer.parseInt(mm.format(ds));
        int now=h*60+m;
        JSONArray jsonArray=new JSONArray();
        try {
            List<Blowoff> content  =  this.blowoffService.getRtmPoiById();
            int max=0;
            for (int i = 0; i < content.size() ; i++) {
                Blowoff blowoff=content.get(i);
                String firstTime=blowoff.getFirstTime();
                int minute=Integer.parseInt(blowoff.getMinutes());
                int hour=Integer.parseInt(blowoff.getHours());
                num[(hour*60+minute)/15]++;
            }
            for (int i = 0; i < num.length ; i++) {
                if(max<num[i]){
                    max=num[i];
                }
            }
            JSONObject jsonobject=new JSONObject();
            int i=(h*60+m)/15;
            jsonobject.put("co",String.format("%.2f",num[i]*10.1));
            jsonobject.put("co2",String.format("%.2f",num[i]*300.2));
            jsonobject.put("co2ImgWidth",161*num[i]/max);
            jsonobject.put("co2Scale","98%");
            //co2Threshold
            jsonobject.put("co2Threshold",500000*num[i]/max);
            jsonobject.put("coScale","100%");
            jsonobject.put("coThreshold",2000*num[i]/max);
            jsonobject.put("hc",String.format("%.2f",num[i]*1.1));
            jsonobject.put("hcImgWidth",164*num[i]/max);
            jsonobject.put("hcScale","99.8%");
            jsonobject.put("hcThreshold",300*num[i]/max);
            jsonobject.put("maxTime",sdf.format(ds));
            jsonobject.put("nox",String.format("%.2f",num[i]*2.1));
            jsonobject.put("noxImgWidth",218*num[i]/max);
            jsonobject.put("noxScale","100%");
            jsonobject.put("noxThreshold",500*num[i]/max);
            jsonobject.put("oil",String.format("%.2f",num[i]*100.2));
            jsonobject.put("oilImgWidth",128*num[i]/max);
            jsonobject.put("oilScale","77.7%");
            jsonobject.put("oilThreshold",200000*num[i]/max);
            jsonobject.put("pm",String.format("%.2f",num[i]*1.1/11));
            jsonobject.put("pmImgWidth",47*num[i]/max);
            jsonobject.put("pmScale","29.1%");
            jsonobject.put("pmThreshold",100*num[i]/max);

            response.setContentType("text/html;charset = UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(callback + "(" + jsonobject.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/data/map")
    public void mapJson(HttpServletRequest request, HttpServletResponse response) {
        String callback  =  request.getParameter("callback");
        Date ds=new Date();
        Date yesterday = new Date(ds.getTime() - 86400000L);
        String date=ymd.format(ds);
        String yester=ymd.format(yesterday);
        int h=Integer.parseInt(hh.format(ds));
        int m=Integer.parseInt(mm.format(ds));
        int now=h*60+m;
        JSONArray jsonArray=new JSONArray();
        try {
            List<MapJson> content  =  this.blowoffService.selectBlowoffMap();
            for (int i = 0; i < content.size() ; i++) {
                String time="";
                MapJson mapJson=content.get(i);
                String days=mapJson.getDays();
                int hours=Integer.parseInt(mapJson.getHours());
                if(hours<10){
                    time="0"+hours;
                }else {
                    time=hours+"";
                }
                String lon=mapJson.getLon();
                String lat=mapJson.getLat();
                int val = (int)(Math.random()*100+1);
                JSONArray js=new JSONArray();
                js.add(days);
                js.add(time);
                js.add(lon);
                js.add(lat);
                js.add(val);
                jsonArray.add(js);
            }

            response.setContentType("text/html;charset = UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(callback + "(" + jsonArray.toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
