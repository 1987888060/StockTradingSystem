package com.zxy.controller;

import com.zxy.entity.NewsInfo;
import com.zxy.service.NewsInfoService;
import com.zxy.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsInfoController {

    @Autowired
    private NewsInfoService newsInfoService;

    @RequestMapping("/newsinfo")
    public ResultData selectAll(){
        List<NewsInfo> newsInfos = newsInfoService.selectAll();
        return ResultData.success(newsInfos);
    }
}
