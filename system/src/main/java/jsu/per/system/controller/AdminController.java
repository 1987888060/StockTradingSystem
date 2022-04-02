package jsu.per.system.controller;

import jsu.per.system.DTO.AdminDTO;
import jsu.per.system.DTO.LoginDTO;
import jsu.per.system.DTO.UserDTO;
import jsu.per.system.pojo.Admin;
import jsu.per.system.pojo.User;
import jsu.per.system.result.JsonResult;
import jsu.per.system.service.AdminService;
import jsu.per.system.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {


    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;


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
     * 删除用户
     */

    /**
     * 添加用户
     */

    /**
     * 添加管理员
     */

    /**
     * 删除管理员
     */

    /**
     *
     */

}
