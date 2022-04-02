package jsu.per.system.service;

import jsu.per.system.pojo.Admin;
import jsu.per.system.pojo.User;

import java.util.List;

public interface AdminService {

    Admin getAdminBy(String adminname);

    /**
     * 根据id获取用户
     * @param id
     * @return 用户
     */
    Admin getAdminBy(int id);

    List<Admin> getUsersBy(String adminname);

    List<Admin> getAllUser();

    /**
     * 添加用户
     */
    void addAdmin(Admin admin);

    /**
     * 更新
     */
    void updateAdmin(Admin admin);

    /**
     * 删除
     */
    void deleteAdmin(int adminid);

    String login(int id);

    void logout(String token);
}
