package com.zxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zxy.entity.HistoryTrade;
import com.zxy.entity.PickedStock;
import com.zxy.mapper.HistoryTradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryTradeServiceImpl implements HistoryTradeService {
    @Autowired
    HistoryTradeMapper historyTradeMapper;

    @Override
    public void storage(HistoryTrade trade) {
        historyTradeMapper.insert(trade);
    }

    @Override
    public HistoryTrade readById(int id) {
        return historyTradeMapper.selectById(id);
    }

    @Override
    public List<HistoryTrade> readAll() {
        return historyTradeMapper.selectList(null);
    }

    @Override
    public List<HistoryTrade> readByUserid(int userid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);

        List list = historyTradeMapper.selectList(queryWrapper);

        return list;
    }

    @Override
    public List<HistoryTrade> readByCode(String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("stockcode",code);

        List list = historyTradeMapper.selectList(queryWrapper);

        return list;
    }

    @Override
    public List<HistoryTrade> readByCode(int userid, String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("stockcode",code);

        List list = historyTradeMapper.selectList(queryWrapper);

        return list;
    }

    @Override
    public List<HistoryTrade> readByType(int type) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type",type);

        List list = historyTradeMapper.selectList(queryWrapper);

        return list;
    }

    @Override
    public List<HistoryTrade> readByType(int userid, int type) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("type",type);

        List list = historyTradeMapper.selectList(queryWrapper);

        return list;
    }

    @Override
    public PageInfo<HistoryTrade> selectAll(Integer page, Integer limit, Integer userid) {
        PageHelper.startPage(page,limit);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        PageInfo<HistoryTrade> info = new PageInfo<HistoryTrade>(historyTradeMapper.selectList(queryWrapper));
        return info;
    }

    @Override
    public void remove(Integer id) {
        historyTradeMapper.deleteById(id);
    }


}
