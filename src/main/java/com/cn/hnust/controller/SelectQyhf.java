package com.cn.hnust.controller;

import com.cn.hnust.pojo.DateInfo;
import com.cn.hnust.pojo.SelectQyhfStartup;
import com.cn.hnust.service.IRtmTripService;
import org.apache.commons.collections.ArrayStack;
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
 * Created by Administrator_xu on 2017/10/19.
 */
@Controller
public class SelectQyhf {
    //selectQyhfStartup
    private Logger logger  =  LoggerFactory.getLogger(RtmTripController.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sms = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat ymd2 = new SimpleDateFormat("yyyyMMdd");

    @Resource
    private IRtmTripService rtmTripService;
    //车均日总出行
    @ResponseBody
    @RequestMapping("/selectQyhfStartup.do")
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

        HashMap<String,List<SelectQyhfStartup>> hashmap=new HashMap<String,List<SelectQyhfStartup>>();
        try {
            List<SelectQyhfStartup> content  =  this.rtmTripService.selectQyhfStartup(map);
            Long sdt=ymd2.parse(sdate).getTime();
            Long edt=ymd2.parse(edate).getTime();
            long  space=60*15*100;
            int len=(int)(Math.ceil((edt-sdt)/(space)));
            for (int i = 0; i <content.size() ; i++) {
                SelectQyhfStartup selectQyhfStartup=content.get(i);
                String name=selectQyhfStartup.getName();
                String startime=selectQyhfStartup.getStartup_time();
                if(hashmap.get(name)==null){
                    List<SelectQyhfStartup> list=new ArrayList<SelectQyhfStartup>();
                    list.add(selectQyhfStartup);
                    hashmap.put(name,list);
                }else {
                    hashmap.get(name).add(selectQyhfStartup);
                }
            }
            HashMap<String,List<Integer>> hasnum=new HashMap<String, List< Integer>>();
            int max=0;
            for (Map.Entry<String, List<SelectQyhfStartup>> m:hashmap.entrySet()) {
                List<SelectQyhfStartup> list =m.getValue();
                List<Integer> li=new ArrayList<Integer>();
                int[] fireup=new int[len];
                Arrays.fill(fireup, 0);
                for (int i = 0; i < list.size(); i++) {
                    String starttime=list.get(i).getStartup_time();
                    Long dt=sdf.parse(starttime).getTime();
                    int spi= (int) Math.ceil((dt-sdt)/space);
                    fireup[spi]++;
                    max++;
                }
                for (int i = 0; i <fireup.length ; i++) {
                    li.add(fireup[i]);
                }
                hasnum.put(m.getKey(),li);
            }
            HashSet<Long> timeDistinct = new HashSet<Long>();
            Map<String, Object> result = new HashMap<String, Object>();
            HashMap<String, ArrayList<Integer>> res = new HashMap<String, ArrayList<Integer>>();
            ArrayList<Long> timeSeries = new ArrayList<Long>();
            int sum=0;
            for (Map.Entry<String, List<Integer>> hash:hasnum.entrySet()) {
                String k=hash.getKey();
                List<Integer> list=hash.getValue();
                ArrayList<Integer> arry=new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    sum++;
                    arry.add(list.get(i));
                    timeDistinct.add(i*space+sdt);
                }
                res.put(k,arry);
            }
            for( Long t : timeDistinct )
                timeSeries.add(t);
            Collections.sort(timeSeries);
            DataSeries series = new DataSeries();
            series.setCatergory1("区域点火数统计");
            series.setType("Double");
            series.setUnit("个");
            result.put("status", 0);
            result.put("message", "");
            result.put("timestamp", timeSeries);
            result.put("dataLength", timeSeries.size());
            result.put("title", "区域点火数统计");
            result.put("series", res);
            response.setContentType("text/html;charset = UTF-8");
            response.getWriter().write(callback + "(" + new JSONObject(result).toString() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
