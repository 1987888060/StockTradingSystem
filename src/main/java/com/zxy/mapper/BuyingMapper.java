package com.zxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxy.entity.Buying;
import com.zxy.entity.Selling;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BuyingMapper extends BaseMapper<Buying> {

    @Select("SELECT stockcode,SUM(num) as num,price  FROM `buying` WHERE stockcode = #{stockcode} GROUP BY price  ORDER BY price DESC")
    List<Buying> getListByPrice(String stockcode);
}
