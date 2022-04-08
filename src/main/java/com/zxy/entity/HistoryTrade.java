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
@TableName("historytrade")
public class HistoryTrade {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private int userid;
    private Date time;
    private String stockname;
    private String stockcode;
    //交易量
    private int num;
    //交易额
    private double volume;
    //单价
    private double price;
    //交易类型 0 -> 卖出 ; 1 -> 买入
    private int type;
}
