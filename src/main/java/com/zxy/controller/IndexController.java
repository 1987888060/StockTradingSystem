package com.zxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    /**
     * 主页路由
     * 运行进入主页：新闻页面
     * @return 默认主页是login
     */
    @RequestMapping("/")
    public String toNews() {
        // return "page/news";
        return "page/dapan";
    }

    /**
     * 进入登录页面
     * @return
     */
    @RequestMapping("/login")
    public String toLogin() {
        return "login";
    }

    /**
     * 进入登录页面
     * @return
     */
    @RequestMapping("/login1")
    public String toLogin1() {
        return "login1";
    }

    /**
     * 进入注册页面
     * @return
     */
    @RequestMapping("/toReg")
    public String toReg(){
        return "register";
    }



    /**
     * 进入大盘详情页面
     * @return
     */
    @RequestMapping("/toDapan")
    public String toDapan(){
        return "/page/dapan";
    }

    /**
     * 进入后台交易页面
     * @return
     */
    @RequestMapping("/toIndex")
    public String toIndex(){
        return "/page/index";
    }


}
