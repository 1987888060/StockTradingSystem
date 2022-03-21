package jsu.per.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jsu.per.system.dao.UserMapper;
import jsu.per.system.pojo.User;
import jsu.per.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserBy(String username) {

        User user = new User();
        user.setUsername(username);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(user);

        List<User> users = userMapper.selectList(userQueryWrapper);
        if(users.size()>1){
            //日志打印 级别error
            log.error("账号对应多个用户");
            //出现多个用户 返回空值
            return null;
        }else if (users.size() == 1){
            return users.get(0);
        }else{
            //未查询到结果
            return null;
        }

    }

    @Override
    public User getUserBy(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public void AddUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void UpdateUser(User user) {
        userMapper.updateById(user);
    }
}

