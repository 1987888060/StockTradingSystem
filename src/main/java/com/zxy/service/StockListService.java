package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.entity.Stock_List;

import java.util.List;

public interface StockListService extends IService<Stock_List> {
    List<Stock_List> stock_lists();
}
