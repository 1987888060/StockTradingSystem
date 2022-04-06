package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.entity.Stock;
import com.zxy.entity.Stock_List;

import java.io.IOException;
import java.util.List;


public interface StockService  {
    //获取所有股票信息
    List<Stock> stock_lists() throws IOException;

    //获取单个
    Stock getStockByCode(String stockcode) throws IOException;
}
