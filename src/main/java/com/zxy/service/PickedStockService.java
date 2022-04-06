package com.zxy.service;


import com.zxy.entity.PickedStock;

import java.util.List;

public interface PickedStockService {

    /**
     * 添加自选
     */
    void add(int userid,String code);

    /**
     * 删除自选
     */
    void delete(int userid,String code);

    /**
     * 查询所有
     */
    List<PickedStock> getAll();

    /**
     * 根据userid查询
     * @param userid
     */
    List<PickedStock> getByUserid(int userid);


}
