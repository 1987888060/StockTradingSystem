package jsu.per.system.shiro;

import jsu.per.system.pojo.User;
import jsu.per.system.service.RoleService;
import jsu.per.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

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
        roles.add(""+user.getRole_id());
        info.setRoles(roles);
        //设置用户许可（user.getPerms（）是一个Set<String>，【blog:read,blog:search。。。】）

        Set<String> perms = new HashSet<String>();
        List<Integer> rights = roleService.findRight(user.getRole_id());
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
        //登录TOKEN，包含了用户账号密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        //下列多个判断可根据业务自行增删
        // 判断用户名是否不存在，如果不存在抛出异常
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        //通过用户名，从数据库中查询出用户信息
        User user = userService.getUserBy(username);
        //如果用户不存在，则抛出账号不存在异常，由控制器决定返回消息为账号或密码错误
        if (user == null) {
            throw new UnknownAccountException("No account found for admin [" + username + "]");
        }

        //构造验证信息返回
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return info;
    }
}
