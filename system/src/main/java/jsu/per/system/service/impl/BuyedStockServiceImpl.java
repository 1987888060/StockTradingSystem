package jsu.per.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jsu.per.system.dao.BuyedStockMapper;
import jsu.per.system.pojo.BuyedStock;
import jsu.per.system.pojo.PickedStock;
import jsu.per.system.pojo.Stock;
import jsu.per.system.pojo.StockSimple;
import jsu.per.system.service.BuyedStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyedStockServiceImpl implements BuyedStockService {
    @Autowired
    private BuyedStockMapper buyedStockMapper;

    @Override
    public void buy(int userid, String code, int num) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("code",code);

        BuyedStock buyedStock = buyedStockMapper.selectOne(queryWrapper);

        //以前没有买过
        if (buyedStock == null){
            buyedStock = new BuyedStock();
            buyedStock.setCode(code);
            buyedStock.setUserid(userid);
            buyedStock.setNum(num);
            buyedStockMapper.insert(buyedStock);
        }else{//以前买过
            buyedStock.setNum(buyedStock.getNum()+num);
            buyedStockMapper.updateById(buyedStock);
        }
    }

    @Override
    public void sell(BuyedStock buyedStock) {
        //全部出售
        if (buyedStock.getNum() == 0){
            buyedStockMapper.deleteById(buyedStock.getId());
        }
        //部分出售
        if (buyedStock.getNum()>0){
            buyedStockMapper.updateById(buyedStock);
        }
    }

    @Override
    public List<BuyedStock> selectBy(int userid) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);

        List<BuyedStock> list =  buyedStockMapper.selectList(queryWrapper);

        return  list;
    }

    @Override
    public List<BuyedStock> selectAll() {
        return buyedStockMapper.selectList(null);
    }

    @Override
    public BuyedStock selectBy(int userid, String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("code",code);

        return   buyedStockMapper.selectOne(queryWrapper);

    }

    @Override
    public List<StockSimple> getBuyed(int userid) {
        List<StockSimple> list = buyedStockMapper.getBuyed(userid);
        return list;
    }

}
