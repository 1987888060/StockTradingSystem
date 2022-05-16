package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.entity.Stock;
import com.zxy.entity.Stock_List;

import java.io.IOException;
import java.util.List;


public interface StockService  {

    //获取单个
    Stock getStockByCode(String stockcode) throws IOException;

    void insert(Stock stock);

    void update(Stock stock);

    void deleteByCode(String code);
}
