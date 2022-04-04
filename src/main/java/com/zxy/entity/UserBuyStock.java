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
@TableName(value = "user_buy_stock")
public class UserBuyStock {

    @TableId(type = IdType.AUTO) // 主键自增策略
    private Integer id;

    private Integer user_id;

    private Integer stock_id;

    public UserBuyStock(Integer user_id, Integer stock_id) {
        this.user_id = user_id;
        this.stock_id = stock_id;
    }
}