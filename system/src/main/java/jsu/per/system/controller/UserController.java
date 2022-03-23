//package jsu.per.system.controller;
//
//import jsu.per.system.DTO.LoginDTO;
//import jsu.per.system.pojo.User;
//import jsu.per.system.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("user")
//public class UserController {
//
//    @Autowired
//    UserService userService;
//
//    /**
//     * 登陆
//     * @param loginDTO
//     * @param bindingResult
//     * @return
//     */
//    @PostMapping("/login.do")
//    public ModelAndView login(@RequestBody @Validated LoginDTO loginDTO, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView();
//        Map<String, Object> result = new HashMap<>();
//        if (bindingResult.hasErrors()) {
//            result.put("status", 400);
//            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
//            modelAndView.addObject(result);
//            modelAndView.setViewName("login");
//            return modelAndView;
//        }
//
//        String username = loginDTO.getUsername();
//        String password = loginDTO.getPassword();
//
//        //这里可以进行加盐处理 先略过
//
//        //用户信息
//        User user = userService.getUserBy(username);
//        //账号不存在、密码错误
//        if (user == null || !user.getPassword().equals(password)) {
//            result.put("status", 400);
//            result.put("msg", "账号或密码有误");
//        } else {
//            //生成token，并保存到redis数据库
//            result = userService.createToken(user.getUserId());
//            result.put("status", 200);
//            result.put("msg", "登陆成功");
//        }
//        modelAndView.addObject(result);
//        modelAndView.setViewName("login");
//        return modelAndView;
//    }
//
//    /**
//     * 退出
//     */
//    @ApiOperation(value = "登出", notes = "参数:token")
//    @PostMapping("/sys/logout")
//    public Map<String, Object> logout(@RequestHeader("token")String token) {
//        Map<String, Object> result = new HashMap<>();
//        shiroService.logout(token);
//        result.put("status", 200);
//        result.put("msg", "您已安全退出系统");
//        return result;
//    }
//}