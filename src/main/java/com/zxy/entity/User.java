package com.zxy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "s_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "stock_nums")
    private Integer stock_nums;

    @TableField(value = "create_time")
    private String create_time;

    @TableField(value = "balance")
    private Integer balance;

    @TableLogic
    @TableField(value = "is_delete")
    private Integer is_delete;
}
