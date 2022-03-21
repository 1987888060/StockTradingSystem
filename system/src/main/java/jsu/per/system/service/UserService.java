package jsu.per.system.service;

import jsu.per.system.pojo.User;

public interface UserService {
    /**
     * 根据账号密码获取用户
     * @param username 账号
     * @param password 密码
     * @return 用户
     * @throws Exception 查询到多个用户 报错
     */
    User getUserBy(String username);

    /**
     * 根据id获取用户
     * @param id
     * @return 用户
     */
    User getUserBy(int id);

    void AddUser(User user);

    void UpdateUser(User user);
}
