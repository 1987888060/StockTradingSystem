package jsu.per.system.controller;


import com.alibaba.fastjson.JSONObject;
import jsu.per.system.DTO.WalletDTO;
import jsu.per.system.pojo.*;
import jsu.per.system.result.JsonResult;
import jsu.per.system.service.*;
import jsu.per.system.utils.JsonTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//用于数据分析
@RestController
@RequestMapping("analyse")
public class AnalyseController {

    @Autowired
    WalletRecordService walletRecordService;
    @Autowired
    WalletService walletService;
    @Autowired
    UserService userService;
    @Autowired
    BuyedStockService buyedStockService;
    @Autowired
    PickedStockService pickedStockService;
    @Autowired
    HistoryTradeService historyTradeService;
    @Autowired
    StockService stockService;
    @Autowired
    StockInfoCacheService stockInfoCacheService;

    //ok
    //获取用户充钱 体现记录
    //然后获取某一段时间内的记录交由前端去进行控制
    @RequiresPermissions("1")
    @GetMapping("/getWalletRecordByUserid.do")
    JsonResult<List<WalletRecord>> getWalletRecord(@PathParam("userid") int userid){
        List list = walletRecordService.readByUserid(userid);

        JsonResult<List<WalletRecord>> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(list);

        return json;
    }

    //ok
    //获取用户充钱 体现记录
    //然后获取某一段时间内的记录交由前端去进行控制
    @RequiresPermissions("1")
    @GetMapping("/getAllWalletRecord.do")
    JsonResult<List<WalletRecord>> getAllWalletRecord(){
        List list = walletRecordService.selectAll();

        JsonResult<List<WalletRecord>> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(list);

        return json;
    }

    //ok
    //获取所有钱包余额
    @RequiresPermissions("1")
    @GetMapping("/getAllWallet.do")
    JsonResult<List<Wallet>> getAllWallet(){
        List list = walletService.selectAll();

        JsonResult<List<Wallet>> json = new JsonResult<>();

        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(list);

        return json;
    }


    //ok
    /**
     * 获取持有的股票
     */
    @RequiresPermissions("1")
    @GetMapping("/getBuyedStockByUserid.do")
    JsonResult<List<BuyedStock>> getBuyedStockByUserid(@PathParam("userid") int userid){
        List list = buyedStockService.selectBy(userid);

        JsonResult<List<BuyedStock>> json = new JsonResult<>();

        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(list);

        return json;
    }

    //ok
    @RequiresPermissions("1")
    @GetMapping("/getAllBuyedStock.do")
    JsonResult<List<BuyedStock>> getBuyedStockByUserid(){
        List list = buyedStockService.selectAll();

        JsonResult<List<BuyedStock>> json = new JsonResult<>();

        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(list);

        return json;
    }

    /**
     * 自选
     */
    @RequiresPermissions("1")
    @GetMapping("/getAllPickedStock.do")
    JsonResult<List<PickedStock>> getAllPickedStock(){
        List<PickedStock> byUserid = pickedStockService.getAll();

        JsonResult<List<PickedStock>> json = new JsonResult<>();

        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(byUserid);
        return json;
    }


    //ok
    /**
     * 自选
     */
    @RequiresPermissions("1")
    @GetMapping("/getPickedStockByUserid.do")
    JsonResult<List<PickedStock>> getPickedStockByUserid(@PathParam("userid") int userid){
        List<PickedStock> byUserid = pickedStockService.getByUserid(userid);

        JsonResult<List<PickedStock>> json = new JsonResult<>();

        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(byUserid);
        return json;
    }


    /**
     * history trade
     */
    //ok
    @RequiresPermissions("1")
    @GetMapping("/getAllHistoryTrade.do")
    JsonResult<List<HistoryTrade>> getAllHistoryTrade(){

        List<HistoryTrade> historyTrades = historyTradeService.readAll();

        JsonResult<List<HistoryTrade>> json = new JsonResult<>();

        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(historyTrades);
        return json;
    }

    //ok
    @RequiresPermissions("1")
    @GetMapping("/getHistoryTradeByUserid.do")
    JsonResult<List<HistoryTrade>> getHistoryTradeByUserid(@PathParam("userid") int userid){

        List<HistoryTrade> historyTrades = historyTradeService.readByUserid(userid);

        JsonResult<List<HistoryTrade>> json = new JsonResult<>();

        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(historyTrades);
        return json;
    }

    //ok
    @RequiresPermissions("1")
    @GetMapping("/getHistoryTradeByCode.do")
    JsonResult<List<HistoryTrade>> getHistoryTradeByUserid(@PathParam("code") String code){

        List<HistoryTrade> historyTrades = historyTradeService.readByCode(code);

        JsonResult<List<HistoryTrade>> json = new JsonResult<>();

        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(historyTrades);
        return json;
    }

    //ok
    @RequiresPermissions("1")
    @GetMapping("/getHistoryTradeByType.do")
    JsonResult<List<HistoryTrade>> getHistoryTradeByType(@PathParam("type") int type){

        List<HistoryTrade> historyTrades = historyTradeService.readByType(type);

        JsonResult<List<HistoryTrade>> json = new JsonResult<>();

        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(historyTrades);
        return json;
    }

    //ok
    @RequiresPermissions("1")
    @GetMapping("/getHistoryTradeByUseridAndCode.do")
    JsonResult<List<HistoryTrade>> getHistoryTradeByUseridAndCode(@PathParam("userid") int userid,@PathParam("code") String code){

        List<HistoryTrade> historyTrades = historyTradeService.readByCode(userid,code);

        JsonResult<List<HistoryTrade>> json = new JsonResult<>();

        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(historyTrades);
        return json;
    }

    //ok
    @RequiresPermissions("1")
    @GetMapping("/getHistoryTradeByUseridAndType.do")
    JsonResult<List<HistoryTrade>> getHistoryTradeByUseridAndType(@PathParam("userid") int userid,@PathParam("type") int type){

        List<HistoryTrade> historyTrades = historyTradeService.readByType(userid,type);

        JsonResult<List<HistoryTrade>> json = new JsonResult<>();

        json.setCode("200");
        json.setMsg("执行成功");
        json.setData(historyTrades);
        return json;
    }

    /**
     * 还差一个 更具时间查询 后面补充
     */

    //ok
    /**
     * 用户财务状况
     */
    @RequiresPermissions("1")
    @GetMapping("/getFinance.do")
    JsonResult<HashMap<String,Object>> getFinance(@PathParam("userid") int userid){
        JsonResult<HashMap<String,Object>> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("操作成功");

        //1、获取所有购买过的彩票
        List<StockSimple> list = buyedStockService.getBuyed(userid);
        //System.out.println(list);
        List<Finance> financeList = new LinkedList<>();
        double money = 0;
        for (StockSimple stockSimple : list){
            String code = stockSimple.getStockcode();
            String name = stockSimple.getStockname();
            List<HistoryTrade> historyTrades = historyTradeService.readByCode(userid, code);

            Finance finance = new Finance();
            finance.setStockcode(code);
            finance.setStockname(name);

            int buy = 0;
            int sell = 0;
            double earnings = 0;
            for (HistoryTrade trade :historyTrades){
                if(trade.getType()==0){//卖出
                    sell += trade.getNum();
                    earnings += trade.getVolume();
                }
                if (trade.getType() == 1){
                    buy += trade.getNum();
                    earnings -= trade.getVolume();
                }
            }

            finance.setBuyed(buy);
            finance.setSell(sell);
            finance.setHave(buy-sell);

            try {
                JSONObject obj = stockService.getStock(code);
                Stock stock = JsonTO.toStock(obj);
                stockInfoCacheService.updateStock(code,stock);
                double price = Double.valueOf(stock.getPrice());
                double a = (buy-sell)*price;
                finance.setValue(a);
                finance.setEarnings(earnings+a);
                money += a;
            } catch (IOException e) {
                e.printStackTrace();
                json.setCode("500");
                json.setMsg("系统错误");
                break;
            }
            financeList.add(finance);
        }

        //System.out.println(financeList);

        HashMap<String,Object> map = new HashMap<>();
        map.put("financeList",financeList);

        money += walletService.getMoney(userid);
        map.put("money",money);

        json.setData(map);
        return  json;
    }


}
