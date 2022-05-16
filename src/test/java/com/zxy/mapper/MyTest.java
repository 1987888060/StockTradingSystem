package com.zxy.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxy.entity.Stock;
import com.zxy.entity.StockSim;
import com.zxy.entity.User;
import com.zxy.service.SellingService;
import com.zxy.util.JsonTO;
import com.zxy.util.OKHTTPUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@SpringBootTest
class MyTest {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private StockSimMapper stockSimMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private SellingService sellingService;

    @Test
    public void test(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        User login = mapper.login(user);
        System.out.println(login);
    }

    @Test
    public void test01() throws IOException {
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
            System.out.println(stock);
        }

    }


    @Test
    public void test02() throws IOException {
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
            String name = split[1].substring(1,a-2);
            StockSim stockSim = new StockSim();
            stockSim.setCode(code);
            stockSim.setName(name);
            stockSimMapper.insert(stockSim);

            JSONObject get = OKHTTPUtil.GET("https://api.doctorxiong.club/v1/stock?code=" + code);
            Stock stock = JsonTO.toStock(get);

            stockMapper.insert(stock);
        }

    }

}