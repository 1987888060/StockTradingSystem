package com.zxy.config;

import com.zxy.util.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置类
 */
// @Configuration
public class ShiroConfig {
	/**
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier ("securityManager") DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		//设置安全管理系统
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		//添加Shiro内置过滤器
		/**
		 * Shiro的内置过滤器，可以实现权限相关的拦截器
		 *  常用的拦截器：
		 *    anon:无需认证（登录）可以访问
		 *    authc:必须认证才可以访问
		 *    user:如果使用rememberMe的功能可以直接访问
		 *    perms:该资源必须资源权限才可以访问
		 *    rele:该资源必须得到角色权限才可以访问
		 *
		 */
		Map<String, String> filterMap = new LinkedHashMap<String, String>();


		//无需验证路由
		filterMap.put("/", "anon");//主页，新闻页面
		filterMap.put("/login", "anon");//登录页面
		filterMap.put("/toDapan", "anon");//大盘详情页面
		// 必须验证路由
		filterMap.put("/*", "authc");//所有请求拦截
		//未验证通过跳转的登录页面
		shiroFilterFactoryBean.setLoginUrl("/login");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 创建DefaultWebSecurityManager
	 */
	@Bean (name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier ("userRealm") UserRealm realm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//关联realm
		securityManager.setRealm(realm);
		return securityManager;
	}

	/**
	 * 创建Realm
	 */
	@Bean (name = "userRealm")
	public UserRealm getRealm() {
		return new UserRealm();
	}
}
