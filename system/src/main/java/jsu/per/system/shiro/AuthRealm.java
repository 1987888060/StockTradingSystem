package jsu.per.system.shiro;

import jsu.per.system.pojo.Admin;
import jsu.per.system.pojo.Login;
import jsu.per.system.pojo.User;
import jsu.per.system.service.*;
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
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    private UserTokenService userTokenService;
    private AdminTokenService adminTokenService;

    @Lazy
    public AuthRealm(UserTokenService service, AdminTokenService adminTokenService){
        this.userTokenService  = service;
        this.adminTokenService = adminTokenService;

    }

    public String getName(){
        return "authRealm";
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        //获取login
        Login login = (Login) getAvailablePrincipal(principals);
        //创建权限对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //设置用户角色（user.getRoles（）是一个Set<String>，【admin,student。。。】）
        Set<String> roles =new HashSet<String>();
        Set<String> perms = new HashSet<String>();

        int id = login.getId();

        if(login.getType() == 0){//管理员
            Admin admin = adminService.getAdminBy(id);
            roles.add(""+admin.getRoleid());
            info.setRoles(roles);

            //设置用户许可（user.getPerms（）是一个Set<String>，【blog:read,blog:search。。。】）
            List<Integer> rights = roleService.findRight(admin.getRoleid());
            for(Integer right:rights){
                perms.add(""+right);
            }
        }
        if (login.getType() == 1){//用户

            User user = userService.getUserBy(id);
            roles.add(""+user.getRoleid());
            info.setRoles(roles);

            //设置用户许可（user.getPerms（）是一个Set<String>，【blog:read,blog:search。。。】）
            List<Integer> rights = roleService.findRight(user.getRoleid());
            for(Integer right:rights){
                perms.add(""+right);
            }

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
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //登录TOKEN，包含了token
        AuthToken upToken = (AuthToken) token;
        String str = (String) upToken.getCredentials();
        int type = upToken.getType();
        System.out.println("authRealm-->"+str);
        //下列多个判断可根据业务自行增删
        // 判断token是否不存在，如果不存在抛出异常
        if (str == null) {
            throw new AccountException("null token");
        }

        Login login = new Login();


        if (type == 0){//管理员

            //通过token从redis中读取user_id
            String admin_id = adminTokenService.isExistKey(str,null);
            System.out.println("admin_id:"+admin_id);
            if(admin_id == null){
                throw new UnknownAccountException("admin_id");
            }
            //更新一下缓存
            adminTokenService.updateToken(str,admin_id);
            int id = Integer.valueOf(admin_id);
            Admin admin = adminService.getAdminBy(id);
            System.out.println(admin);
            //如果用户不存在，则抛出账号不存在异常，由控制器决定返回消息为账号或密码错误
            if (admin == null) {
                log.warn("token查询到的管理员不存在");
                throw new UnknownAccountException("token查询到的管理员不存在");
            }

            login.setId(id);
            login.setType(0);


        }else if(type == 1){//用户
            //通过token从redis中读取user_id
            String user_id = userTokenService.isExistKey(str,null);
            System.out.println("user_id:"+user_id);
            if(user_id == null){
                throw new UnknownAccountException("user_id");
            }
            //更新一下缓存
            userTokenService.updateToken(str,user_id);
            int id = Integer.valueOf(user_id);
            User user = userService.getUserBy(id);
            System.out.println(user);
            //如果用户不存在，则抛出账号不存在异常，由控制器决定返回消息为账号或密码错误
            if (user == null) {
                log.warn("token查询到的用户id对应的用户不存在");
                throw new UnknownAccountException("token查询到的用户id对应的用户不存在");
            }

            login.setId(id);
            login.setType(1);


        }




        //构造验证信息返回
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(login, str, getName());
        return info;
    }

}
