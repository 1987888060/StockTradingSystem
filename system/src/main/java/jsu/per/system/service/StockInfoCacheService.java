package jsu.per.system.service;

import jsu.per.system.pojo.Stock;

public interface StockInfoCacheService {

    /**
     * 添加基本信息
     * @param code 股票代码
     * @param stock 股票基本信息
     * @return
     */
    Stock addStock(String code, Stock stock);

    /**
     *删除
     * @param code
     */
    void deleteStock(String code);

    /**
     * 更新
     * @param code
     * @param stock
     */
    Stock updateStock(String code,Stock stock);

    /**
     * 查找
     * @param code
     * @return
     */
    Stock getStock(String code);
}
