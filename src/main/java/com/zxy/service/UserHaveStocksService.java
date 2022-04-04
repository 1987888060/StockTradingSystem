package com.zxy.service;

import com.github.pagehelper.PageInfo;
import com.zxy.entity.Stocks;

public interface UserHaveStocksService {

    // 用户持有股票数据结果分页
    PageInfo<Stocks> selectAll(Integer page,Integer limit,Integer id);

    // 股票抛出
    int sellStock(Integer id);
}
