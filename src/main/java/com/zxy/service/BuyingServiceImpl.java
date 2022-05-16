package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.entity.Buying;
import com.zxy.entity.Selling;
import com.zxy.mapper.BuyingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyingServiceImpl extends ServiceImpl<BuyingMapper, Buying> implements BuyingService{
    @Autowired
    BuyingMapper buyingMapper;

    @Override
    public List<Buying> get(String stockcode) {
        return buyingMapper.getListByPrice(stockcode);
    }
}
