package com.zxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxy.entity.*;
import com.zxy.mapper.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Component
public class AsyncService implements Runnable  {

    BuyingMapper buyingMapper;
    SellingMapper sellingMapper;
    HistoryTradeMapper historyTradeMapper;
    UserMapper userMapper;
    StockMapper stockMapper;
    UserBuyStockMapper userBuyStockMapper;

    @Autowired
    public AsyncService(BuyingMapper buyingMapper,SellingMapper sellingMapper,HistoryTradeMapper historyTradeMapper,UserMapper userMapper,StockMapper stockMapper,UserBuyStockMapper userBuyStockMapper){
        this.buyingMapper = buyingMapper;
        this.sellingMapper = sellingMapper;
        this.historyTradeMapper = historyTradeMapper;
        this.userMapper = userMapper;
        this.stockMapper = stockMapper;
        this.userBuyStockMapper = userBuyStockMapper;
        new Thread(this).start();
    }

    @SneakyThrows
    @Override
    public void run() {
        aysnc01();
    }


    //项目启动 开启异步线程 进行计算
    public void aysnc01() throws InterruptedException {
        //每隔30秒 查询一次
        for (;;){
            //从buying中取出所有数据
            List<Buying> buyings = buyingMapper.selectList(new QueryWrapper<>());
            //System.out.println(buyings);
            for (Buying buying:buyings){
                String stockcode = buying.getStockcode();
                Integer userid = buying.getUserid();
                double price = buying.getPrice();

                QueryWrapper queryWrapper1 = new QueryWrapper();
                queryWrapper1.eq("code",stockcode);
                Stock stock = stockMapper.selectOne(queryWrapper1);


                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("stockcode",stockcode);
                queryWrapper.ne("userid",userid);
                queryWrapper.le("price",price);

                List<Selling> list = sellingMapper.selectList(queryWrapper);
                //System.out.println(list);

                //取出buying的数量
                Integer num1 = buying.getNum();
                Integer num = num1;

                for (Selling selling:list){

                    //取出selling的数量
                    Integer num2 = selling.getNum();

                    //购买的数量小于出售的数量
                    if (num1<num2){

                        double money = num1 * price;
                        //将钱转给卖的人
                        if (selling.getUserid()!= 0){
                            User user = userMapper.selectById(selling.getUserid());
                            user.setBalance(user.getBalance()+money);
                        }


                        //进行记录
                        HistoryTrade trade = new HistoryTrade();
                        //股票代码
                        trade.setStockcode(stock.getCode());
                        //股票名
                        trade.setStockname(stock.getName());
                        //交易数量
                        trade.setNum(num1);
                        //交易额
                        trade.setVolume(money);
                        //交易单价
                        trade.setPrice(price);
                        //who
                        trade.setUserid(selling.getUserid());
                        //type sell
                        trade.setType(0);
                        //交易时间
                        trade.setTime(new Date());
                        historyTradeMapper.insert(trade);

                        selling.setNum(num2 - num1);
                        sellingMapper.updateById(selling);

                        num1 = 0;

                        break;
                    }else if (num1 == num2){
                        //将钱转给卖的人
                        double money = num1 * price;
                        if (selling.getUserid()!= 0){
                            User user = userMapper.selectById(selling.getUserid());
                            user.setBalance(user.getBalance()+money);
                        }

                        //进行记录
                        HistoryTrade trade = new HistoryTrade();
                        //股票代码
                        trade.setStockcode(stock.getCode());
                        //股票名
                        trade.setStockname(stock.getName());
                        //交易数量
                        trade.setNum(num1);
                        //交易额
                        trade.setVolume(money);
                        //交易单价
                        trade.setPrice(price);
                        //who
                        trade.setUserid(selling.getUserid());
                        //type sell
                        trade.setType(0);
                        //交易时间
                        trade.setTime(new Date());
                        historyTradeMapper.insert(trade);

                        sellingMapper.deleteById(selling.getId());

                        num1 = 0;
                        break;
                    }else{
                        //将钱转给卖的人
                        double money = num2 * price;

                        if (selling.getUserid()!= 0){
                            User user = userMapper.selectById(selling.getUserid());
                            user.setBalance(user.getBalance()+money);
                        }

                        //进行记录
                        HistoryTrade trade = new HistoryTrade();
                        //股票代码
                        trade.setStockcode(stock.getCode());
                        //股票名
                        trade.setStockname(stock.getName());
                        //交易数量
                        trade.setNum(num2);
                        //交易额
                        trade.setVolume(money);
                        //交易单价
                        trade.setPrice(price);
                        //who
                        trade.setUserid(selling.getUserid());
                        //type sell
                        trade.setType(0);
                        //交易时间
                        trade.setTime(new Date());
                        historyTradeMapper.insert(trade);

                        sellingMapper.deleteById(selling.getId());

                        num1 = num1 - num2;
                    }
                }

                if (num1 == num){
                    continue;
                }

                //进行记录
                HistoryTrade trade = new HistoryTrade();
                //股票代码
                trade.setStockcode(stock.getCode());
                //股票名
                trade.setStockname(stock.getName());
                //交易数量
                trade.setNum(num-num1);
                //交易额
                trade.setVolume((num - num1)*price);
                //交易单价
                trade.setPrice(price);
                //who
                trade.setUserid(userid);
                //type buy
                trade.setType(1);
                //交易时间
                trade.setTime(new Date());
                historyTradeMapper.insert(trade);

                User user = userMapper.selectById(buying.getUserid());

                //添加到userbuystock列表
                QueryWrapper queryWrapper2 = new QueryWrapper();
                queryWrapper2.eq("username",user.getUsername());
                queryWrapper2.eq("stockcode",stockcode);
                UserBuyStock userBuyStock = userBuyStockMapper.selectOne(queryWrapper2);
                if(userBuyStock == null){
                    userBuyStock = new UserBuyStock();
                    userBuyStock.setStockcode(stock.getCode());
                    userBuyStock.setUsername(user.getUsername());
                    userBuyStock.setStockname(stock.getName());
                    userBuyStock.setNum(num);
                    userBuyStock.setPrice(price);
                    userBuyStockMapper.insert(userBuyStock);
                }else{

                    double total = userBuyStock.getPrice()*userBuyStock.getNum()+trade.getVolume();

                    userBuyStock.setNum(num+userBuyStock.getNum());

                    userBuyStock.setPrice(total/userBuyStock.getNum());
                    userBuyStockMapper.updateById(userBuyStock);
                }


                if (num1 == 0){
                    buyingMapper.deleteById(buying.getId());
                }else{
                    buying.setNum(num1);
                    buyingMapper.updateById(buying);
                }
            }


            Thread.sleep(10000);
        }
    }


}