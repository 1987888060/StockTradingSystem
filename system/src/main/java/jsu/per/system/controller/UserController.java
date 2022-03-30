package jsu.per.system.controller;

import jsu.per.system.DTO.LoginDTO;
import jsu.per.system.DTO.RegisterDTO;
import jsu.per.system.DTO.UserDTO;
import jsu.per.system.pojo.User;
import jsu.per.system.result.JsonResult;
import jsu.per.system.service.UserService;
import jsu.per.system.service.VCodeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private VCodeService vCodeService;

    /**
     * 登陆
     * @param loginDTO
     * @return
     */
    @PostMapping("/login.do")
    public JsonResult<UserDTO> login(@RequestBody @Validated LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        System.out.println(username);
        System.out.println(password);
        User user = userService.getUserBy(username);
        System.out.println(user);

        JsonResult<UserDTO> result = new JsonResult<>();
        System.out.println(user.getPassword().equals(password));
        if (user == null || !user.getPassword().equals(password)){
            result.setCode("400");
            result.setMsg("账号或密码有误");
        }else{
            //登陆成功返回token
            result.setCode("200");
            result.setMsg("登陆成功");
            String token = userService.login(user.getId());
            UserDTO userDTO = new UserDTO();
            userDTO.setToken(token);
            userDTO.setEmail(user.getEmail());
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setRoleid(user.getRoleid());
            userDTO.setPassword(user.getPassword());
            result.setData(userDTO);
        }

        return result;
    }

    /**
     * 退出
     * @param token
     * @return
     */
    @PostMapping("/logout.do")
    public JsonResult<String> logout(@RequestHeader("token") String token) {
        JsonResult<String> result = new JsonResult<>();
        result.setCode("200");
        result.setMsg("安全退出");
        userService.logout(token);
        return result;
    }

    /**
     * 注册
     * @param registerDTO
     * @return
     */
    @PostMapping("/register.do")
    public JsonResult<String> register(@RequestBody RegisterDTO registerDTO){
        JsonResult<String> result = new JsonResult<>();

        //验证码
        String vcode = registerDTO.getVcode();
        String email = registerDTO.getEmail();
        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();
        if(vcode == null){
            result.setCode("403");
            result.setMsg("验证码不正确");
            return result;
        }

        //取出redis中的vcode
        String code = vCodeService.addCode(email, "null");
        //验证码过期
        if(vcode.equals("null")){
            vCodeService.deleteCode(email);
            result.setCode("403");
            result.setMsg("验证码已过期");
            return result;
        }

        //未过期
        if(code.equals(vcode)){//真确
            //查询用户名是否唯一
            List<User> users = userService.getUsersBy(username);
            if(users.size()>0){//存在
                result.setCode("403");
               result.setMsg("用户名已存在");
            }else{//不存在
                User user = new User();
                //默认姓名
                user.setName("用户"+username);
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                //默认角色 --> 普通用户 现在先写为1 后期要改1
                user.setRoleid(1);
                userService.addUser(user);
                result.setCode("200");
                result.setMsg("注册成功，跳转至登陆页面");
                //注册成功后 删除redis中的验证码
                vCodeService.deleteCode(email);
            }
            //查询邮箱是否唯一(以后补充)

        }else{//错误
            result.setCode("403");
            result.setMsg("验证码不正确");
        }
        return result;
    }


    /**
     * 发送验证码
     * @param email
     * @return
     */
    @GetMapping("/sendVerificationCode.do")
    public void sendVerificationCode(@RequestParam String email){
        userService.sendVerificationCode(email);
    }

    // 目的：用于测试shiro框架是否运行正确 结果：正确运行
    @RequiresPermissions("1")
    @GetMapping("/getDemo01.do")
    public List<User> getDemo01(){
        return userService.getAllUser();
    }

    // 目的：用于测试shiro框架是否运行正确 结果：正确运行
    @RequiresPermissions("7")
    @GetMapping("/getDemo02.do")
    public List<User> getDemo02(){
        return userService.getAllUser();
    }
}