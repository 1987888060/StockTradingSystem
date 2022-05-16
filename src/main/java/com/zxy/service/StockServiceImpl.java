package com.zxy.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.entity.Stock;
import com.zxy.entity.Stock_List;
import com.zxy.mapper.StockListMapper;
import com.zxy.mapper.StockMapper;
import com.zxy.util.JsonTO;
import com.zxy.util.OKHTTPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService{

    @Autowired
    StockMapper stockMapper;


    @Override
    public Stock getStockByCode(String stockcode) throws IOException {
        JSONObject get = OKHTTPUtil.GET("https://api.doctorxiong.club/v1/stock?code=" + stockcode);
        Stock stock = JsonTO.toStock(get);
        System.out.println(stock);
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("code",stockcode);
//        Stock stock = stockMapper.selectOne(queryWrapper);
//        System.out.println(stock);

        if(stock == null){
          QueryWrapper queryWrapper = new QueryWrapper();
          queryWrapper.eq("code",stockcode);
          stock = stockMapper.selectOne(queryWrapper);
          System.out.println(stock);
        }
        return stock;
    }

    @Override
    public void insert(Stock stock) {
        stockMapper.insert(stock);
    }

    @Override
    public void update(Stock stock) {
        stockMapper.updateById(stock);
    }

    @Override
    public void deleteByCode(String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code",code);
        stockMapper.delete(queryWrapper);
    }


}
