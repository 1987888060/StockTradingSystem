package jsu.per.system.service;

import jsu.per.system.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 根据用户名获取用户
     * @param username 账号
     * @return 用户
     */
    User getUserBy(String username);

    /**
     * 根据id获取用户
     * @param id
     * @return 用户
     */
    User getUserBy(int id);

    List<User> getUsersBy(String username);

    List<User> getAllUser();

    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);

    /**
     * 更新
     * @param user
     */
    void updateUser(User user);

    /**
     * 删除
     * @param user
     */
    void deleteUser(User user);

    String login(int id);

    void logout(String token);


    void sendVerificationCode(String email);
}
