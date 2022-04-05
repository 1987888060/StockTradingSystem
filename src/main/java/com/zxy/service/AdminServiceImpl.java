package com.zxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.entity.Admin;

import com.zxy.entity.User;
import com.zxy.mapper.AdminMapper;
import com.zxy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService{

    @Autowired
    private AdminMapper mapper;

    @Override
    public Admin login(Admin admin) {
        Admin resu = mapper.login(admin);
        if (resu != null) {
            return resu;
        } else {
            throw new RuntimeException("请检查用户名或密码是否有误");
        }
    }

    @Override
    public int register(Admin admin) {

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",admin.getUsername());

        // 先进行去重
        if (mapper.selectList(wrapper).size() == 0) {
            admin.setCreate_time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            return mapper.insert(admin);
        } else {
            return 0;
        }
    }

    @Override
    public Admin info(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",username);

        List list = mapper.selectList(wrapper);

        return (Admin) list.get(0);

    }

    @Override
    public Integer update(Admin admin) {
       return mapper.updateById(admin);
    }

    @Override
    public Admin selectById(Integer id) {
        return mapper.selectById(id);
    }
}
