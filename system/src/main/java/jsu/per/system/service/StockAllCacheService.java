package jsu.per.system.service;


import jsu.per.system.pojo.Stock;

import java.util.HashMap;
import java.util.Set;

public interface StockAllCacheService {


    /**
     * 更新
     * @param code
     * @param name
     */
    String updateStock1(String code,  String name);

    String updateStock2(String name,  String code);

    /**
     * 模糊查找
     * @param str
     * @return key-value集合
     */
    Set<Stock> getKeyValue(String str);

    /**
     * 通过code查询是否存在
     */
    boolean isExist(String code);

    Stock getStock(String code);
}
