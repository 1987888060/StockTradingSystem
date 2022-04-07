package com.zxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxy.entity.PickedStock;
import com.zxy.entity.UserBuyStock;
import com.zxy.mapper.PickedStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PickedStockServiceImpl implements PickedStockService {

    @Autowired
    PickedStockMapper pickedStockMapper;

    @Override
    public void add(int userid,String code,String stockname) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("code",code);

        if (pickedStockMapper.selectOne(queryWrapper) == null){
            PickedStock pickedStock = new PickedStock();
            pickedStock.setUserid(userid);
            pickedStock.setCode(code);
            pickedStock.setStockname(stockname);
            pickedStockMapper.insert(pickedStock);
        }

    }

    @Override
    public void delete(int userid,String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("code",code);
        PickedStock pickedStock = pickedStockMapper.selectOne(queryWrapper);

        if (pickedStock != null){
            pickedStockMapper.deleteById(pickedStock.getId());
        }
    }

    @Override
    public List<PickedStock> getAll() {
        List<PickedStock> pickedStocks = pickedStockMapper.selectList(null);
        return pickedStocks;
    }

    @Override
    public List<PickedStock> getByUserid(int userid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);

        List list = pickedStockMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public PageInfo<PickedStock> selectAll(Integer page, Integer limit, Integer userid) {
        PageHelper.startPage(page,limit);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        PageInfo<PickedStock> info = new PageInfo<PickedStock>(pickedStockMapper.selectList(queryWrapper));
        return info;
    }
}
