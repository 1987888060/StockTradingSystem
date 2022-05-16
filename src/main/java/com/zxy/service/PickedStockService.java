package com.zxy.service;


import com.github.pagehelper.PageInfo;
import com.zxy.entity.PickedStock;
import com.zxy.entity.UserBuyStock;

import java.util.List;

public interface PickedStockService {

    /**
     * 添加自选
     */
    void add(int userid,String code,String stockname);

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

    PageInfo<PickedStock> selectAll(Integer page, Integer limit, Integer userid);

    void deleteByID(Integer id);

    void deleteByCode(String code);

    void deleteByUserid(Integer id);
}
