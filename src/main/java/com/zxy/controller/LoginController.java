package com.zxy.controller;

import com.zxy.entity.Admin;
import com.zxy.entity.User;
import com.zxy.service.AdminService;
import com.zxy.service.UserService;
import com.zxy.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class LoginController {

	@Autowired
	private UserService service;

	@Autowired
	private AdminService adminService;

	/**
	 * 用户登录
	 * <p>
	 * // * @param user    用户实体类
	 * // * @param request session作用域
	 *
	 * @return json数据
	 */
	@PostMapping ("/login")
	public ResultData login(User user, HttpServletRequest request) {
		try {
			User resu = service.login(user);
			System.out.println(resu);
			request.getSession().setAttribute("user", resu);
			return ResultData.success(1, resu);
		} catch (
				Exception e) {
			return ResultData.fail(e.getMessage());
		}

	}

	@PostMapping ("/login1")
	public ResultData adminlogin(Admin admin, HttpServletRequest request) {
		try {
			Admin resu = adminService.login(admin);
			System.out.println(resu);
			request.getSession().setAttribute("admin", resu);
			return ResultData.success(1, resu);
		} catch (
				Exception e) {
			return ResultData.fail(e.getMessage());
		}

	}

	/**
	 * 用户注册
	 *
	 * @param user 注册表单实体对象
	 * @return 返回注册数据
	 */
	@PostMapping ("/register")
	public ResultData register(User user) {
		int resu = service.register(user);
		if (resu > 0) {
  			return ResultData.success(1, user);
		} else {
			return ResultData.fail("用户已存在，请检查后再进行注册");
		}
	}

	/**
	 * 个人中心主页数据
	 *
	 * @param username 用户名
	 * @return json数据
	 */
	@PostMapping ("/info")
	public ResultData info(String username) {
		System.out.println(username);
		try {
			User info = service.info(username);
			return ResultData.success(info);
		} catch (Exception e) {
			return ResultData.fail(e.getMessage());
		}
	}

	@PostMapping ("/changeinfo")
	public ResultData changeinfo(Integer  id,String password) {
		User user = service.selectById(id);
		user.setPassword(password);
		Integer integer = service.update(user);
		if (integer > 0) {
			return ResultData.success(1, user);
		}
		return ResultData.fail("更新失败，请联系网站管理员");
	}




}
