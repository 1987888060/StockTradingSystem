package com.zxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxy.entity.StockSim;
import com.zxy.entity.UserBuyStock;
import com.zxy.mapper.StockSimMapper;
import com.zxy.mapper.UserBuyStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

@Service
public class UserBuyStockServiceImpl extends ServiceImpl<UserBuyStockMapper, UserBuyStock>  implements UserBuyStockService {

    @Autowired
    private UserBuyStockMapper userBuyStockMapper;

    @Override
    public int insert(UserBuyStock userBuyStock){
        // 插入购买后的数据
        return userBuyStockMapper.insert(userBuyStock);
    }

    @Override
    public UserBuyStock find(String username, String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        queryWrapper.eq("stockcode",code);
       return userBuyStockMapper.selectOne(queryWrapper);
    }

    @Override
    public void update(UserBuyStock userBuyStock) {
        userBuyStockMapper.updateById(userBuyStock);
    }

    @Override
    public PageInfo<UserBuyStock> selectAll(Integer page, Integer limit, String username) {
        PageHelper.startPage(page,limit);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        PageInfo<UserBuyStock> info = new PageInfo<UserBuyStock>(userBuyStockMapper.selectList(queryWrapper));
        return info;
    }

}
