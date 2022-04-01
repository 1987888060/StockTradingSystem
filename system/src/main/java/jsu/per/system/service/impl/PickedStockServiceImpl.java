package jsu.per.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jsu.per.system.dao.PickedStockMapper;
import jsu.per.system.pojo.PickedStock;
import jsu.per.system.service.PickedStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PickedStockServiceImpl implements PickedStockService {

    @Autowired
    PickedStockMapper pickedStockMapper;

    @Override
    public void add(int userid,String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("code",code);

        if (pickedStockMapper.selectOne(queryWrapper) == null){
            PickedStock pickedStock = new PickedStock();
            pickedStock.setUserid(userid);
            pickedStock.setCode(code);
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
}
