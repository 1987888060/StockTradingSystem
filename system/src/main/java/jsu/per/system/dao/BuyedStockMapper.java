package jsu.per.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jsu.per.system.pojo.BuyedStock;
import jsu.per.system.pojo.Stock;
import jsu.per.system.pojo.StockSimple;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuyedStockMapper extends BaseMapper<BuyedStock> {
    List<StockSimple> getBuyed(int userid);
}
