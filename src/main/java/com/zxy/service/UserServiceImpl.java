package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.entity.User;
import com.zxy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


	@Autowired
	private UserMapper mapper;

	@Override
	public User login(User user) {
		User resu = mapper.login(user);
		if (resu != null) {
			return resu;
		} else {
			throw new RuntimeException("请检查用户名或密码是否有误");
		}
	}

	@Override
	public int register(User user) {
		// 先进行去重
		if (mapper.findUserByName(user.getUsername()) != null) {
			return 0;
		} else {
			user.setCreate_time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			return mapper.insert(user);
		}
	}

	@Override
	public User info(String username) {
		User info = mapper.info(username);
		if (info != null) {
			return info;
		} else {
			throw new RuntimeException("请检查用户名是否有误");
		}
	}

	@Override
	public Integer update(User user) {
		int update = mapper.update(user);
		return update;
	}

	@Override
	public User list(User user) {
		return mapper.list(user);
	}

	//根据id查询
	@Override
	public User selectByIds(Integer id) {
		return mapper.selectByIds(id);
	}


	@Override
	public User selectById(Integer id) {
		return mapper.selectById(id);
	}
}
