package com.zxy.service;

import com.zxy.entity.NewsInfo;
import com.zxy.mapper.NewsInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsInfoServiceImpl implements NewsInfoService {


    @Autowired
    private NewsInfoMapper newsInfoMapper;

    @Override
    public List<NewsInfo> selectAll() {
        return newsInfoMapper.selectAll();
    }
}
