package jsu.per.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jsu.per.system.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Integer> findRight(int id);
}
