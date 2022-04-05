package com.zxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxy.entity.Admin;
import com.zxy.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    Admin login(Admin admin);
}
