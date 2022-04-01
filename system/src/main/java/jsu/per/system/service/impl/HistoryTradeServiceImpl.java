package jsu.per.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jsu.per.system.dao.HistoryTradeMapper;
import jsu.per.system.pojo.HistoryTrade;
import jsu.per.system.service.HistoryTradeService;
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
        queryWrapper.eq("code",code);

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


}
