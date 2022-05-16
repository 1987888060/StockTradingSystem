package com.zxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxy.entity.Selling;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SellingMapper extends BaseMapper<Selling> {

    @Select("SELECT stockcode,SUM(num) as num,price  FROM `selling` WHERE stockcode = #{stockcode} GROUP BY price ORDER BY price ASC")
    List<Selling> getListByPrice(String stockcode);
}