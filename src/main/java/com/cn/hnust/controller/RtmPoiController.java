package com.cn.hnust.controller;

import com.alibaba.fastjson.JSONObject;
import com.cn.hnust.pojo.RtmPoi;
import com.cn.hnust.pojo.RtmTrip;
import com.cn.hnust.service.IRtmPoiService;
import com.cn.hnust.service.IRtmTripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator_xu on 2017/10/12.
 */
@Controller
@RequestMapping("/rtmpoi")
public class RtmPoiController {
    @Resource
    private IRtmPoiService rtmPoiService;

    @ResponseBody
    @RequestMapping("/all")
    public void toIndex(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String Callback = request.getParameter("Callback");//客户端请求参数
        Map<String,String> map = new HashMap<String,String>();

        RtmPoi rtmPoi = this.rtmPoiService.getRtmPoiById();
        map.put("result", rtmPoi.toString());
        (new org.json.simple.JSONObject(map)).toJSONString();
        PrintWriter out = response.getWriter();
        response.setContentType("text/json; charset=UTF-8");
        out.flush();
        out.close();
    }
}
