package jsu.per.system.controller;

import jsu.per.system.DTO.AdminDTO;
import jsu.per.system.DTO.LoginDTO;
import jsu.per.system.DTO.UserDTO;
import jsu.per.system.pojo.Admin;
import jsu.per.system.pojo.User;
import jsu.per.system.result.JsonResult;
import jsu.per.system.service.AdminService;
import jsu.per.system.service.RoleService;
import jsu.per.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {


    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;


    /**
     * 登陆
     */
    //ok
    @PostMapping("/login.do")
    public JsonResult<AdminDTO> login(@RequestBody @Validated LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        System.out.println(username);
        System.out.println(password);
        Admin admin = adminService.getAdminBy(username);
        System.out.println(admin);

        JsonResult<AdminDTO> result = new JsonResult<>();
        System.out.println(admin.getPassword().equals(password));
        if (admin == null || !admin.getPassword().equals(password)){
            result.setCode("400");
            result.setMsg("账号或密码有误");
        }else{
            //登陆成功返回token
            result.setCode("200");
            result.setMsg("登陆成功");
            String token = adminService.login(admin.getId());
            AdminDTO adminDTO = new AdminDTO();
            adminDTO.setToken(token);
            adminDTO.setRolename(roleService.selectById(admin.getRoleid()).getName());
            result.setData(adminDTO);
        }

        return result;
    }

    /**
     * 退出
     */
    @PostMapping("/logout.do")
    public JsonResult<String> logout(@RequestHeader("token") String token) {
        JsonResult<String> result = new JsonResult<>();
        result.setCode("200");
        result.setMsg("安全退出");
        adminService.logout(token);
        return result;
    }

    /**
     * 删除用户
     */
    @RequiresPermissions("1")
    @DeleteMapping("/deleteUser.do")
    public JsonResult<String> deleteUser(@PathParam("userid") int userid){
        JsonResult<String> result = new JsonResult<>();
        result.setCode("200");
        result.setMsg("删除成功");
        userService.deleteUser(userid);
        return result;
    }


    /**
     * 添加管理员
     */
    @RequiresPermissions("1")
    @DeleteMapping("/addAdmin.do")
    public JsonResult<String> addAdmin(@RequestBody Admin admin ){
        JsonResult<String> result = new JsonResult<>();
        result.setCode("200");
        result.setMsg("执行成功");
        result.setData("添加管理员成功");
        adminService.addAdmin(admin);
        return result;
    }


    /**
     * 删除管理员
     */
    @RequiresPermissions("1")
    @DeleteMapping("/deleteAdmin.do")
    public JsonResult<String> delectAdmin(@PathParam("adminid") int adminid){
        JsonResult<String> result = new JsonResult<>();
        result.setCode("200");
        result.setMsg("执行成功");
        result.setData("删除管理员成功");
        adminService.deleteAdmin(adminid);
        return result;
    }


    /**
     * 修改管理员权限
     */
    @RequiresPermissions("1")
    @PutMapping("/updateAdmin.do")
    public JsonResult<String> updateAdmin(@RequestBody Admin admin){
        JsonResult<String> result = new JsonResult<>();
        result.setCode("200");
        result.setMsg("执行成功");
        result.setData("修改权限成功");
        adminService.updateAdmin(admin);
        return result;
    }

    /**
     * 修改用户
     */
    @RequiresPermissions("1")
    @PutMapping("/updateUser.do")
    public JsonResult<String> updateUser(@RequestBody User user){
        JsonResult<String> result = new JsonResult<>();
        result.setCode("200");
        result.setMsg("执行成功");
        result.setData("修改成功");
        userService.updateUser(user);
        return result;
    }

    //ok
    //获取所有用户信息
    @RequiresPermissions("1")
    @GetMapping("/getAllUser.do")
    JsonResult<List<User>> getAllUser(){
        List list = userService.getAllUser();

        JsonResult<List<User>> json = new JsonResult<>();

        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(list);

        return json;
    }


}
