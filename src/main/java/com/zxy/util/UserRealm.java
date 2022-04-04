package com.zxy.util;

import com.zxy.entity.User;
import com.zxy.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	/**
	 * 执行授权逻辑
	 * @param arg0
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("执行授权逻辑");
		return null;
	}

	/**
	 * 执行认证逻辑
	 * @param arg0
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("执行认证逻辑");

		//编写shiro判断逻辑，判断用户名和密码
		//1.判断用户名
		UsernamePasswordToken token=(UsernamePasswordToken) arg0;
		User user=userService.info(token.getUsername());
		if (user==null){
			//用户名不存在
			return null;//shiro底层会抛出UnknownAccountException
		}
		//2.判断密码
		return new SimpleAuthenticationInfo("",user.getPassword(),"");
	}
}
