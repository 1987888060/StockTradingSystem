package jsu.per.system.service;


import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Date;

public interface StockService {
    //获取所有的股票 只获取股票名和股票代码
    //只有沪深的
    JSONObject getAllStock() throws IOException;

    //分时数据
    JSONObject getStockMin(String code) throws IOException;

    //日K数据
    //默认type 为 0  0->不复权 1->前复权 2->后复权
    JSONObject getKLineOfDay(String code, String startDate, String endDate,int type) throws IOException;

    //周K数据
    JSONObject getKLineOfWeek(String code, String startDate, String endDate,int type) throws IOException;

    //月K数据
    JSONObject getKLineOfMonth(String code, String startDate, String endDate,int type) throws IOException;

    //获取全部指数
    JSONObject getAllIndex() throws IOException;

    //获取股票基础信息
    JSONObject getStock(String code) throws IOException;

    JSONObject getStockRank(JSONObject jsonObject) throws IOException;

    //获取全部行业板块(包含基本板块信息,已按业绩排序)
    JSONObject getIndustry() throws IOException;

}
