package com.zxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxy.entity.Stock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockMapper extends BaseMapper<Stock> {
}
