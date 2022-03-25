package jsu.per.system.shiro;

import jsu.per.system.pojo.User;
import jsu.per.system.service.RoleService;
import jsu.per.system.service.UserService;
import jsu.per.system.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    private UserTokenService userTokenService;

    @Lazy
    public UserRealm(UserTokenService service){
        userTokenService  = service;
    }

    public String getName(){
        return "userRealm";
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        //获取当前用户对应的User对象
        User user = (User) getAvailablePrincipal(principals);
        //创建权限对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //设置用户角色（user.getRoles（）是一个Set<String>，【admin,student。。。】）
        Set<String> roles =new HashSet<String>();
        roles.add(""+user.getRoleid());
        info.setRoles(roles);
        //设置用户许可（user.getPerms（）是一个Set<String>，【blog:read,blog:search。。。】）

        Set<String> perms = new HashSet<String>();
        List<Integer> rights = roleService.findRight(user.getRoleid());
        System.out.println("11111111");
        System.out.println(rights);
        for(Integer right:rights){
            perms.add(""+right);
        }

        info.setStringPermissions(perms);
        return info;
    }

    /**
     * 登陆
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //登录TOKEN，包含了token
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String str = (String) upToken.getCredentials();
        System.out.println("UserRealm-->"+str);
        //下列多个判断可根据业务自行增删
        // 判断token是否不存在，如果不存在抛出异常
        if (str == null) {
            throw new AccountException("null token");
        }
        //通过token从redis中读取user_id
        String user_id = userTokenService.isExistKey(str,null);
        System.out.println(user_id);
        if(user_id == null){
            throw new UnknownAccountException("user_id");
        }
        int id = Integer.valueOf(user_id);
        User user = userService.getUserBy(id);
        System.out.println(user);
        //如果用户不存在，则抛出账号不存在异常，由控制器决定返回消息为账号或密码错误
        if (user == null) {
            log.warn("token查询到的用户id对应的用户不存在");
            throw new UnknownAccountException("token查询到的用户id对应的用户不存在");
        }

        //构造验证信息返回
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, str, getName());
        return info;
    }
}
