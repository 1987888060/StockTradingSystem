package jsu.per.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jsu.per.system.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> getAllUser();
}
