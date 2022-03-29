package jsu.per.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jsu.per.system.DTO.RegisterDTO;
import jsu.per.system.dao.UserMapper;
import jsu.per.system.pojo.User;
import jsu.per.system.result.JsonResult;
import jsu.per.system.service.UserService;
import jsu.per.system.service.UserTokenService;
import jsu.per.system.service.VCodeService;
import jsu.per.system.utils.CodeUtil;
import jsu.per.system.utils.EmailUtil;
import jsu.per.system.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private UserTokenService userTokenService;

    private VCodeService vCodeService;

    @Lazy
    public UserServiceImpl(UserTokenService service,VCodeService vCodeService){
        this.userTokenService  = service;
        this.vCodeService = vCodeService;
    }

    public User getUserBy(String username) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);

        List<User> users = userMapper.selectList(queryWrapper);
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

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    @Override
    public User getUserBy(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getUsersBy(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);

        List<User> users = userMapper.selectList(queryWrapper);
        return users;
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    /**
     * 添加
     * @param user
     */
    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    /**
     * 更新
     * @param user
     */
    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    /**
     * 删除
     * @param user
     */
    @Override
    public void deleteUser(User user) {
        userMapper.updateById(user);
    }

    /**
     * 登陆 生成 token
     * @param id 用户id
     * @return token
     */
    @Override
    public String login(int id) {
        String user_id = ""+id;
        String token = null;
        try {
            token = TokenUtil.creatToken(user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userTokenService.addToken(token,user_id);
        return token;
    }

    /**
     * 退出 删除 缓存中的token
     * @param token
     */
    @Override
    public void logout(String token) {
        userTokenService.deleteToken(token);
    }


    @Override
    public void sendVerificationCode(String email) {
        String code = CodeUtil.getCode();
        String msg = "你的验证码为:"+code+"。此邮件无需回复！！！";
        EmailUtil.sendVerificationCode(email,"Stock Trading System",msg);
        String s = vCodeService.updateCode(email, code);
        if(!code.equals(s)){
            log.warn("存储的code与存入的code不一致");
        }
    }


}

