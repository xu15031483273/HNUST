package com.cn.hnust.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator_xu on 2017/10/23.
 */
@Controller
public class DemoController {

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return  "qqq";
    }
}
