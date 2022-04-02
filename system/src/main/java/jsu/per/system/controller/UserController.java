package jsu.per.system.controller;

import jsu.per.system.DTO.LoginDTO;
import jsu.per.system.DTO.UserDTO;
import jsu.per.system.pojo.Login;
import jsu.per.system.pojo.User;
import jsu.per.system.pojo.WalletRecord;
import jsu.per.system.result.JsonResult;
import jsu.per.system.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private VCodeService vCodeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletRecordService walletRecordService;

    private UserTokenService userTokenService;

    @Lazy
    public UserController(UserTokenService userTokenService){
        this.userTokenService = userTokenService;
    }

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
            //实际上应该设置为空值 先暂时有值
            userDTO.setPassword(user.getPassword());
            userDTO.setRolename(roleService.selectById(user.getRoleid()).getName());
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
     * @param userDTO
     * @return
     */
    @PostMapping("/register.do")
    public JsonResult<String> register(@RequestBody UserDTO userDTO){
        JsonResult<String> result = new JsonResult<>();

        //验证码
        String vcode = userDTO.getVcode();
        String email = userDTO.getEmail();
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

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

                //创建钱包
                Integer id = userService.getUserBy(user.getUsername()).getId();
                walletService.buildWallet(id);

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

    /**
     * 发送验证码 通过token来发送email
     * @param
     * @return
     */
    @GetMapping("/sendVerificationCode1.do")
    public void sendVerificationCode1(@RequestBody UserDTO userDTO){
        //获取token
        String token = userDTO.getToken();
        String user_id = userTokenService.isExistKey(token,null);
        System.out.println("user_id:"+user_id);

        if(user_id == null){
            //删除刚刚的缓存
            userTokenService.deleteToken(token);
        }
        int id = Integer.valueOf(user_id);
        User user = userService.getUserBy(id);
        String email = user.getEmail();
        userService.sendVerificationCode(email);
    }

    //ok
    /**
     * 更新用户信息
     * @param userDTO
     * @return
     */
    @RequiresPermissions("1")
    @PutMapping("/updateInfo.do")
    public JsonResult<UserDTO> updateInfo(@RequestBody UserDTO userDTO){
        JsonResult<UserDTO> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("更新成功");

        String token = userDTO.getToken();
        //先通过传过来的token找到对应user_id
        String user_id = userTokenService.isExistKey(token,null);
        System.out.println("user_id:"+user_id);

        if(user_id == null){
            json.setCode("400");
            json.setMsg("请保持token一致");
            //删除刚刚的缓存
            userTokenService.deleteToken(token);
            return json;
        }
        int id = Integer.valueOf(user_id);
        User user = userService.getUserBy(id);
        //在这个功能点只能修改昵称，其他暂时不能修改 等以后扩展
        user.setName(userDTO.getName());
        user.setEmail(null);
        user.setPassword(null);
        user.setUsername(null);
        userService.updateUser(user);

        userDTO.setPassword("null");

        json.setData(userDTO);

        return json;
    }

    //ok
    /**
     *解除绑定邮箱 第二种方式发送email
     */
    @RequiresPermissions("1")
    @DeleteMapping("/deleteEmail.do")
    public JsonResult<UserDTO> deleteEmail(@RequestBody UserDTO userDTO){
        JsonResult<UserDTO> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("解绑成功");

        String token = userDTO.getToken();
        //先通过传过来的token找到对应user_id
        String user_id = userTokenService.isExistKey(token,null);
        System.out.println("user_id:"+user_id);

        if(user_id == null){
            json.setCode("400");
            json.setMsg("请保持token一致");
            //删除刚刚的缓存
            userTokenService.deleteToken(token);
            return json;
        }
        int id = Integer.valueOf(user_id);
        User user = userService.getUserBy(id);

        //验证码
        String vcode = userDTO.getVcode();

        if(vcode == null){
            json.setCode("403");
            json.setMsg("验证码不正确");
            return json;
        }

        //获取email
        String email = user.getEmail();

        //取出redis中的vcode
        String code = vCodeService.addCode(email, "null");
        //验证码过期
        if(vcode.equals("null")){
            vCodeService.deleteCode(email);
            json.setCode("403");
            json.setMsg("验证码已过期");
            return json;
        }

        //未过期
        if(code.equals(vcode)){//真确
            //将email删除
            user.setEmail("null");
            userService.updateUser(user);
        }else{//错误
            json.setCode("403");
            json.setMsg("验证码不正确");
        }

        return json;
    }

    //ok
    /**
     * 绑定新邮箱 第一种方式发送email
     * @param userDTO
     * @return
     */
    @RequiresPermissions("1")
    @PutMapping("/updateEmail.do")
    public JsonResult<UserDTO> updateEmail(@RequestBody UserDTO userDTO){
        JsonResult<UserDTO> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("更新成功");

        String email = userDTO.getEmail();
        String vcode = userDTO.getVcode();

        //取出redis中的vcode
        String code = vCodeService.addCode(email, "null");
        //验证码过期
        if(vcode.equals("null")){
            vCodeService.deleteCode(email);
            json.setCode("403");
            json.setMsg("验证码已过期");
            return json;
        }

        //未过期
        if(code.equals(vcode)){//真确
           //更新email
            String token = userDTO.getToken();
            //先通过传过来的token找到对应user_id
            String user_id = userTokenService.isExistKey(token,null);
            System.out.println("user_id:"+user_id);

            if(user_id == null){
                json.setCode("400");
                json.setMsg("更新失败，请保持token一致");
                //删除刚刚的缓存
                userTokenService.deleteToken(token);
                return json;
            }
            int id = Integer.valueOf(user_id);
            User user = userService.getUserBy(id);

            if (user.getEmail().equals("null")){//修改
                user.setEmail(email);
                userService.updateUser(user);
                json.setData(userDTO);
            }else{//先解绑
                json.setCode("403");
                json.setMsg("请先解绑");
            }

        }else{//错误
            json.setCode("403");
            json.setMsg("验证码不正确");
        }
        return json;
    }

    /**
     * 获取信息
     * @param userDTO
     * @return
     */
    @RequiresPermissions("1")
    @GetMapping("/getUserInfo.do")
    public JsonResult<UserDTO> getUserInfo(@RequestBody UserDTO userDTO){
        JsonResult<UserDTO> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("查询成功");
        //获取token
        String token = userDTO.getToken();
        //先通过传过来的token找到对应user_id
        String user_id = userTokenService.isExistKey(token,null);
        System.out.println("user_id:"+user_id);

        if(user_id == null){
            json.setCode("403");
            json.setMsg("请保持token一致");
            //删除刚刚的缓存
            userTokenService.deleteToken(token);
            return json;
        }
        int id = Integer.valueOf(user_id);
        User user = userService.getUserBy(id);
        System.out.println(user);
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setEmail(user.getEmail());
        userDTO1.setId(user.getId());
        userDTO1.setUsername(user.getUsername());
        userDTO1.setRoleid(user.getRoleid());
        userDTO1.setPassword(null);
        userDTO1.setName(user.getName());
        userDTO1.setRolename(roleService.selectById(user.getRoleid()).getName());

        json.setData(userDTO1);

        return json;
    }


    //ok
    /**
     * 更新密码  第二种方式发送验证码
     */
    @RequiresPermissions("1")
    @PutMapping("/updatePassword.do")
    public JsonResult<UserDTO> updatePassword(@RequestBody UserDTO userDTO){
        JsonResult<UserDTO> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("更新成功");

        String token = userDTO.getToken();
        //先通过传过来的token找到对应user_id
        String user_id = userTokenService.isExistKey(token,null);
        System.out.println("user_id:"+user_id);

        if(user_id == null){
            json.setCode("400");
            json.setMsg("更新失败，请保持token一致");
            //删除刚刚的缓存
            userTokenService.deleteToken(token);
            return json;
        }
        int id = Integer.valueOf(user_id);
        User user = userService.getUserBy(id);

        //验证码
        String vcode = userDTO.getVcode();

        if(vcode == null){
            json.setCode("403");
            json.setMsg("验证码不正确");
            return json;
        }

        //获取email
        String email = user.getEmail();

        if (email == "null"){
            json.setCode("200");
            json.setMsg("请先绑定email");
            return json;
        }

        //取出redis中的vcode
        String code = vCodeService.addCode(email, "null");
        //验证码过期
        if(vcode.equals("null")){
            vCodeService.deleteCode(email);
            json.setCode("403");
            json.setMsg("验证码已过期");
            return json;
        }

        //未过期
        if(code.equals(vcode)){//真确
            //更新password
            user.setPassword(userDTO.getPassword());
            userService.updateUser(user);
        }else{//错误
            json.setCode("403");
            json.setMsg("验证码不正确");
        }


        return json;
    }

    //ok
    //充钱 支付宝弄好了 再改
    @RequiresPermissions("1")
    @PutMapping("/chargeMoney.do")
    public JsonResult<String> chargeMoney(@PathParam("money") double money){
        JsonResult<String> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("操作成功");

        Login login = (Login)SecurityUtils.getSubject().getPrincipal();
        int userid = login.getId();

        walletService.saving(userid,money);

        WalletRecord record = new WalletRecord();

        record.setMoney(money);
        record.setUserid(userid);
        record.setTime(new Date(System.currentTimeMillis()));
        record.setType(1);

        walletRecordService.saving(record);

        json.setData("充值成功");
        return json;
    }

    //ok
    /**
     * 提现
     */
    @RequiresPermissions("1")
    @PutMapping("/withdrawMoney.do")
    public JsonResult<String> withdrawMoney(@PathParam("money") double money){
        JsonResult<String> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("操作成功");

        Login login = (Login)SecurityUtils.getSubject().getPrincipal();
        int userid = login.getId();

        boolean remove = walletService.remove(userid, money);
        if (remove){
            json.setData("提现成功");
            WalletRecord record = new WalletRecord();

            record.setMoney(money);
            record.setUserid(userid);
            record.setTime(new Date(System.currentTimeMillis()));
            record.setType(0);

            walletRecordService.saving(record);



        }else{
            json.setData("提现失败，余额不足");
        }


        return json;
    }

    //ok
    /**
     * 获取钱包余额
     */
    @RequiresPermissions("1")
    @GetMapping("/getMoney.do")
    public JsonResult<Double> getMoney(){
        JsonResult<Double> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("操作成功");
        Login login = (Login)SecurityUtils.getSubject().getPrincipal();
        int userid = login.getId();

        double money = walletService.getMoney(userid);
        json.setData(money);

        return json;
    }
}