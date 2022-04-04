package com.zxy.controller;

import com.zxy.entity.User;
import com.zxy.service.UserService;
import com.zxy.vo.ResultData;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class LoginController {

	@Autowired
	private UserService service;

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
	//Lu 4.11 up
	// @PostMapping ("/tologin")
	// @RequestMapping ("/tologin")
	// public String login(String username,String password, Model model) {
	// public String login(User user, HttpServletRequest request) {
	// 	System.out.println("name="+username);
	// 	System.out.println("password="+password);
	// 	// String username=request.getParameter("username");
	// 	// String password=request.getParameter("password");
	// 	/**
	// 	 * 使用Shiro编写验证操作
	// 	 */
	// 	//1获取Subject
	// 	Subject subject = SecurityUtils.getSubject();
	//
	// 	//2.封装用户数据
	// 	UsernamePasswordToken token = new UsernamePasswordToken(username, password);
	//
	// 	//3.执行登录方法
	// 	try {
	// 		//登录成功
	// 		subject.login(token);
	// 		return "redirect:/toIndex";
	// 	} catch (UnknownAccountException e) {
	// 		// e.printStackTrace();
	// 		//登录失败：用户名不存在
	// 		model.addAttribute("msg", "用户名不存在");
	// 		return "/login";
	// 	} catch (IncorrectCredentialsException e) {
	// 		// e.printStackTrace();
	// 		//登陆失败：密码错误
	// 		model.addAttribute("msg", "密码错误");
	// 		return "/login";
	// 	}
	// }
	//Lu 4.11 up END

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
	public ResultData changeinfo(User user) {
		System.out.println("修改个人信息界面接收的参数：" + user);
		Integer integer = service.update(user);
		if (integer > 0) {
			return ResultData.success(1, user);
		}
		return ResultData.fail("更新失败，请联系网站管理员");
	}

}
