package com.zxy.service;

import com.zxy.entity.Admin;

public interface AdminService {
    Admin login(Admin admin);

    int register(Admin admin);

    Admin info(String username);

    Integer update(Admin admin);

    //根据id查询
    Admin selectById(Integer id);

}
