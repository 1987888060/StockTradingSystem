package com.zxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxy.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 登录
     * @param user 用户实体类
     * @return 返回登录信息
     */
    User login(User user);

    User info(String username);

    User list(User user);
    //开市
    void upkai();
    //休市
    void upxiu();

    Integer update(User user);

    Integer updateBalance(User user);

    @Select("select * from s_user where username = #{username}")
    User findUserByName(String username);

    //根据id查询
    @Select("select * from s_user where id = #{id}")
    User selectByIds(Integer id);
}
