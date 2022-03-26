package jsu.per.system.controller;

import jsu.per.system.DTO.LoginDTO;
import jsu.per.system.DTO.RegisterDTO;
import jsu.per.system.DTO.UserDTO;
import jsu.per.system.pojo.User;
import jsu.per.system.result.JsonResult;
import jsu.per.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

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
//    @PostMapping("/register.do")
//    public JsonResult<String> register(@RequestBody RegisterDTO registerDTO){
//        registerDTO.
//        userService.register(registerDTO);
//        JsonResult<String> result = new JsonResult<>();
//        result.setCode("200");
//        result.setMsg("注册成功");
//        return result;
//    }

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