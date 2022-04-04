package com.zxy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stocks {

    @TableId
    private Integer id;

    private String daima;

    private String mingcheng;

    private String zuoshou;

    private String shiyinglv;

    private String price;

    private String head;

    private String date;
}
