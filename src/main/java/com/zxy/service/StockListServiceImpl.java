package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.entity.Stock_List;
import com.zxy.mapper.StockListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockListServiceImpl extends ServiceImpl<StockListMapper, Stock_List> implements StockListService {

    @Autowired
    private StockListMapper mapper;

    @Override
    public List<Stock_List> stock_lists() {
        return mapper.selectList(null);
    }
}
