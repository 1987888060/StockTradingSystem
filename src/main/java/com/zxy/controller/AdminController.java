package com.zxy.controller;

import com.zxy.entity.Admin;
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




}
