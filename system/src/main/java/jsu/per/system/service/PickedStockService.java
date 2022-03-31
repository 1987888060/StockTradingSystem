package jsu.per.system.service;

import jsu.per.system.pojo.PickedStock;

public interface PickedStockService {

    /**
     * 添加自选
     */
    void add(int userid,String code);

    /**
     * 删除自选
     */
    void delete(int userid,String code);
}
