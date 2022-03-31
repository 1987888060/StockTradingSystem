package jsu.per.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jsu.per.system.pojo.User;
import jsu.per.system.pojo.Wallet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalletMapper extends BaseMapper<Wallet> {
}
