package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zxy.entity.StockSim;
import com.zxy.entity.UserBuyStock;

import javax.swing.*;

public interface UserBuyStockService extends IService<UserBuyStock> {
    public int insert(UserBuyStock userBuyStock);

    public UserBuyStock find(String username,String code);

    public void update(UserBuyStock userBuyStock);

    PageInfo<UserBuyStock> selectAll(Integer page, Integer limit, String username);
}
