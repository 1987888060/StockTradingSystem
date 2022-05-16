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
@TableName("stockinfo")
public class Stock {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //股票名
    private String name;
    //股票代码
    private String code;
    //GP 股票 ZS 指数
    private String type;
    //今日开盘价
    private String open;
    //昨日收盘价
    private String close;
    //实时价格
    private String price;
    //开盘后价格变化
    private String priceChange;
    //价格变化，单位为百分比
    private String changePercent;
    //开盘后最高价
    private String high;
    //开盘后最低价
    private String low;
    //成交量 单位 手
    private String volume;
    //成交额 单位 万
    private String turnover;
    //换手率 单位 百分比
    private String turnoverRate;
    //总市值 单位 亿
    private String totalWorth;
    //流通市值 单位 亿
    private String circulationWorth;
    //数据更新时期
    private String date;
    //市净率
    private String pb;
    //静态市盈率
    private String spe;
    //市盈率
    private String pe;


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof Stock) {
            Stock stock = (Stock) obj;

            // 比较每个属性的值一致时才返回true
            if (stock.name.equals(this.name) )
                return true;
        }
        return false;
    }

    /**
     * 重写hashcode方法，返回的hashCode一样才再去比较每个属性的值
     */
    @Override
    public int hashCode() {
        return name.hashCode() * code.hashCode();
    }

}
