package com.zxy.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.entity.Stock;
import com.zxy.entity.Stock_List;
import com.zxy.mapper.StockListMapper;
import com.zxy.util.JsonTO;
import com.zxy.util.OKHTTPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService{

    @Override
    public List<Stock> stock_lists() throws IOException {
        List<Stock> list = new LinkedList<>();
        JSONObject jsonObject = OKHTTPUtil.GET("https://api.doctorxiong.club/v1/stock/all");
        String data = jsonObject.get("data").toString();
        JSONArray array = JSONObject.parseArray(data);
        int len = array.size();
        for (int i = 0; i < len; i++) {
            String[] split = array.get(i).toString().split(",");
            System.out.println(array.get(i).toString());
            int a = split[0].length();
            String code = split[0].substring(2,a-1);
            a = split[1].length();
            JSONObject get = OKHTTPUtil.GET("https://api.doctorxiong.club/v1/stock?code=" + code);
            Stock stock = JsonTO.toStock(get);
            list.add(stock);
        }
        return list;
    }

    @Override
    public Stock getStockByCode(String stockcode) throws IOException {
        JSONObject get = OKHTTPUtil.GET("https://api.doctorxiong.club/v1/stock?code=" + stockcode);
        Stock stock = JsonTO.toStock(get);
        return stock;
    }
}
