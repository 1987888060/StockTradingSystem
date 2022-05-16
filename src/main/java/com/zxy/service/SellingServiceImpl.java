package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.entity.Selling;
import com.zxy.mapper.SellingMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellingServiceImpl extends ServiceImpl<SellingMapper, Selling> implements SellingService{

    @Autowired
    SellingMapper sellingMapper;

    @Override
    public List<Selling> get(String stockcode) {
       return sellingMapper.getListByPrice(stockcode);
    }
}
