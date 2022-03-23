//package jsu.per.system.Realm;
//
//import jsu.per.system.pojo.User;
//import jsu.per.system.service.UserService;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.util.ByteSource;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class UserRealm extends AuthorizingRealm {
//
//    @Autowired
//    UserService userService;
//
//    //构造器初始化
//    public UserRealm(){
//        HashedCredentialsMatcher passwordMatcher = new HashedCredentialsMatcher("md5");
//        //加密3次
//        passwordMatcher.setHashIterations(3);
//        //密码注入到Realm
//        this.setCredentialsMatcher(passwordMatcher);
//    }
//
//    /**
//     * 授权
//     * @param principalCollection
//     * @return
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        return null;
//    }
//
//    /**
//     * 认证
//     * @param authenticationToken
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        //用户输入的用户信息
//        String username = (String) authenticationToken.getPrincipal();
//
//        //盐
//        String salt = "cg";
//
//        //根据用户名获取用户密码
//        User user = userService.getUserBy(username);
//        String password = user.getPassword();
//        if(password == null){
//            throw new UnknownAccountException("账号或密码错误");
//        }
//
//        //返回数据库获取账号信息的凭证
//        return new SimpleAuthenticationInfo(username,password, ByteSource.Util.bytes(salt),getName());
//    }
//}
