package com.zxy.controller;

import com.zxy.entity.Admin;
import com.zxy.entity.User;
import com.zxy.service.AdminService;
import com.zxy.vo.ResultData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService service;

    /**
     * 添加管理员
     */
    @PostMapping("/addAdmin")
    public ResultData addAdmin(@RequestBody Admin admin){
        int register = service.register(admin);
        if (register > 0) {
            return ResultData.success(1, register);
        } else {
            return ResultData.fail("用户已存在，请检查后再进行注册");
        }
    }

    @PostMapping ("/admininfo")
    public ResultData info(String username) {
        System.out.println(username);
        try {
            Admin info = service.info(username);
            return ResultData.success(info);
        } catch (Exception e) {
            return ResultData.fail(e.getMessage());
        }
    }

    @PostMapping ("/changeadmininfo")
    public ResultData changeadmininfo(String username,String password) {
        Admin admin = service.info(username);
        admin.setPassword(password);
        Integer integer = service.update(admin);
        if (integer > 0) {
            return ResultData.success(1, admin);
        }
        return ResultData.fail("更新失败，请联系网站管理员");
    }


}
