package com.zxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxy.entity.UserBuyStock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBuyStockMapper extends BaseMapper<UserBuyStock> {
    List<UserBuyStock> selectAll(Integer id);
}
