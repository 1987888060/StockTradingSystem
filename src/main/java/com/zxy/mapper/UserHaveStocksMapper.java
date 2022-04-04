package com.zxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxy.entity.Stocks;
import com.zxy.entity.UserHaveStocks;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHaveStocksMapper extends BaseMapper<UserHaveStocks> {

    List<Stocks> selectAll(Integer id);

    // 股票抛出
    int sellStock(Integer id);
}
