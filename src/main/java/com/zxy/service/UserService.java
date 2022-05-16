package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.entity.User;

public interface UserService extends IService<User> {
    /**
     * 接收user对象进行登录验证
     *
     * @param user user实体类
     * @return 返回完整的user对象
     */
    User login(User user);

    int register(User user);

    User info(String username);

    Integer update(User user);

    User list(User user);
    //根据id查询
    User selectByIds(Integer id);

    //
    User selectById(Integer id);
}
