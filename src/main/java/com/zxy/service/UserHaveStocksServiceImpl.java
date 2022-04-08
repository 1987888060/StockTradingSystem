package com.zxy.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxy.entity.Stocks;
import com.zxy.entity.UserBuyStock;
import com.zxy.mapper.UserHaveStocksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHaveStocksServiceImpl implements UserHaveStocksService {

    @Autowired
    private UserHaveStocksMapper userHaveStocksMapper;

    @Override
    public PageInfo<UserBuyStock> selectAll(Integer page, Integer limit, String username) {
        PageHelper.startPage(page,limit);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        PageInfo<UserBuyStock> info = new PageInfo<UserBuyStock>(userHaveStocksMapper.selectList(queryWrapper));
        return info;
    }

    @Override
    public int sellStock(Integer id) {
        return userHaveStocksMapper.sellStock(id);
    }
}
