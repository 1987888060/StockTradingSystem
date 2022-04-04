package com.zxy.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxy.entity.Stocks;
import com.zxy.mapper.UserHaveStocksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHaveStocksServiceImpl implements UserHaveStocksService {

    @Autowired
    private UserHaveStocksMapper userHaveStocksMapper;

    @Override
    public PageInfo<Stocks> selectAll(Integer page, Integer limit, Integer id) {
        PageHelper.startPage(page,limit);
        PageInfo<Stocks> info = new PageInfo<>(userHaveStocksMapper.selectAll(id));
        return info;
    }

    @Override
    public int sellStock(Integer id) {
        return userHaveStocksMapper.sellStock(id);
    }
}
