package jsu.per.system.service;

import jsu.per.system.pojo.HistoryTrade;

import java.util.List;

public interface HistoryTradeService {

    //存储
    void storage(HistoryTrade trade);

    //读取 按照id
    HistoryTrade readById(int id);

    //所有
    List<HistoryTrade> readAll();

    //读取 按照userid
    List<HistoryTrade> readByUserid(int userid);

    //读取 按照股票代码
    List<HistoryTrade> readByCode(String code);

    //读取 按照股票代码
    List<HistoryTrade> readByCode(int userid,String code);

    //读取 按照type
    List<HistoryTrade> readByType(int type);

    //读取 按照type
    List<HistoryTrade> readByType(int userid,int type);
}
