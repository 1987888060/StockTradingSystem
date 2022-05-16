package com.zxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "selling")
public class Selling {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userid;
    private String stockcode;
    private double price;
    private Integer num;
    private Date date;
}
