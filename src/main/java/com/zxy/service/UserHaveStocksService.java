package com.zxy.service;

import com.github.pagehelper.PageInfo;
import com.zxy.entity.Stocks;
import com.zxy.entity.UserBuyStock;

public interface UserHaveStocksService {

    // 用户持有股票数据结果分页
    PageInfo<UserBuyStock> selectAll(Integer page, Integer limit, String username);

    // 股票抛出
    int sellStock(Integer id);
}
