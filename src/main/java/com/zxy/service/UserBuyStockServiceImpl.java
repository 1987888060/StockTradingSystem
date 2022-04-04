package com.zxy.service;

import com.zxy.entity.UserBuyStock;
import com.zxy.mapper.UserBuyStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBuyStockServiceImpl implements UserBuyStockService {

    @Autowired
    private UserBuyStockMapper userBuyStockMapper;

    @Override
    public int insert(UserBuyStock userBuyStock){
        // 插入购买后的数据
        return userBuyStockMapper.insert(userBuyStock);
    }
}
