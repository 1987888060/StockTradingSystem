package jsu.per.system.service;

import jsu.per.system.pojo.BuyedStock;
import jsu.per.system.pojo.Stock;
import jsu.per.system.pojo.StockSimple;

import java.util.List;

public interface BuyedStockService {

    /**
     * 购买股票
     */
    void buy(int userid,String code,int num);


    /**
     * 出售股票
     */
    void sell(BuyedStock buyedStock);

    /**
     * 查询
     */
    List<BuyedStock> selectBy(int userid);

    /**
     * 查询
     */
    List<BuyedStock> selectAll();

    /**
     * 是否拥有该股票
     */
    BuyedStock selectBy(int userid,String code);

    /**
     * 获取用户买过的股票代码和股票名
     * @param userid
     * @return
     */
    List<StockSimple> getBuyed(int userid);

}
