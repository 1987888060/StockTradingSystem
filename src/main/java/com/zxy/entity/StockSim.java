package com.zxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("stock")
public class StockSim {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //股票代码
    private String name;
    //股票名
    private String code;
}
