package jsu.per.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jsu.per.system.DTO.StockDTO;
import jsu.per.system.pojo.BuyedStock;
import jsu.per.system.pojo.Stock;
import jsu.per.system.pojo.User;
import jsu.per.system.result.JsonResult;
import jsu.per.system.service.*;
import jsu.per.system.utils.JsonTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Set;


@RestController
@RequestMapping("stock")
public class StockController {

    @Autowired
    StockAllCacheService stockAllCacheService;
    @Autowired
    StockInfoCacheService stockInfoCacheService;
    @Autowired
    StockService stockService;
    @Autowired
    PickedStockService pickedStockService;
    @Autowired
    WalletService walletService;
    @Autowired
    BuyedStockService buyedStockService;

    /**
     * 查询股票
     * @param msg
     * @return
     */
    @RequiresPermissions("1")
    @GetMapping("/getSomeStock.do")
    public JsonResult<Set<Stock>> getSomeStock(@PathParam("msg") String msg){
        System.out.println(msg);
        JsonResult<Set<Stock>> json = new JsonResult<>();
        Set<Stock> keyValue = stockAllCacheService.getKeyValue(msg);
        json.setCode("200");
        if (keyValue.size()==0){
            json.setMsg("没有找到响应的股票");
        }else{
            json.setMsg("查询成功");
            json.setData(keyValue);
        }
        return json;
    }

    @RequiresPermissions("1")
    @GetMapping("/getStockByCode.do")
    public JsonResult<Stock> getStockInfoByCode(@PathParam("code") String code) {
        JsonResult<Stock> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("查询成功");

        stockInfoCacheService.deleteStock(code);
        try {
            JSONObject jsonObject = stockService.getStock(code);
            Stock stock = JsonTO.toStock(jsonObject);
            //更新到缓存中去
            stockInfoCacheService.updateStock(stock.getCode(),stock);
            json.setData(stock);
        } catch (Exception e) {
            json.setCode("500");
            json.setMsg("查询失败");
        }

        return json;
//        //线程缓存里面查询
//        Stock stock = stockInfoCacheService.getStock(code);
//
//        if (stock == null){//不存在
//
//
//        }else{
//            json.setData(stock);
//        }

    }

    //已验证
    @RequiresPermissions("1")
    @GetMapping("/getStockMin.do")
    public JsonResult<JSONObject> getStockMin(@PathParam("code") String code) {
        JsonResult<JSONObject> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("查询成功");
        try {
            JSONObject stockMin = stockService.getStockMin(code);
            String data = stockMin.get("data").toString();
            JSONObject object = JSONObject.parseObject(data);
            json.setData(object);
        } catch (Exception e) {
            json.setCode("500");
            json.setMsg("查询失败");
        }

        return json;
    }

    //已验证
    @RequiresPermissions("1")
    @GetMapping("/getKLineOfDay.do")
    public JsonResult<JSONArray> getKLineOfDay(@PathParam("code") String code,@PathParam("startDate") String startDate,@PathParam("endDate") String endDate,@PathParam("type") String type ) {
        JsonResult<JSONArray> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("查询成功");
        try {
            JSONObject day = stockService.getKLineOfDay(code,startDate,endDate,Integer.valueOf(type));
            String data = day.get("data").toString();
            JSONArray object = JSONObject.parseArray(data);
            json.setData(object);
        } catch (Exception e) {
            e.printStackTrace();
            json.setCode("500");
            json.setMsg("查询失败");
        }

        return json;

    }

    //已验证
    @RequiresPermissions("1")
    @GetMapping("/getKLineOfWeek.do")
    public JsonResult<JSONArray> getKLineOfWeek(@PathParam("code") String code,@PathParam("startDate") String startDate,@PathParam("endDate") String endDate,@PathParam("type") String type) {
        JsonResult<JSONArray> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("查询成功");
        try {
            JSONObject week = stockService.getKLineOfWeek(code,startDate,endDate,Integer.valueOf(type));
            String data = week.get("data").toString();
            JSONArray object = JSONObject.parseArray(data);
            json.setData(object);
        } catch (Exception e) {
            e.printStackTrace();
            json.setCode("500");
            json.setMsg("查询失败");
        }

        return json;

    }

    //已验证
    @RequiresPermissions("1")
    @GetMapping("/getKLineOfMouth.do")
    public JsonResult<JSONArray> getKLineOfMouth(@PathParam("code") String code,@PathParam("startDate") String startDate,@PathParam("endDate") String endDate,@PathParam("type") String type) {
        JsonResult<JSONArray> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("查询成功");
        try {
            JSONObject month = stockService.getKLineOfMonth(code,startDate,endDate,Integer.valueOf(type));
            String data = month.get("data").toString();
            JSONArray object = JSONObject.parseArray(data);
            json.setData(object);
        } catch (Exception e) {
            e.printStackTrace();
            json.setCode("500");
            json.setMsg("查询失败");
        }

        return json;

    }

    //已验证
    @RequiresPermissions("1")
    @GetMapping("/getAllIndex.do")
    public JsonResult<JSONArray> getAllIndex() {
        JsonResult<JSONArray> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("查询成功");
        try {
            JSONObject stockMin = stockService.getAllIndex();
            String data = stockMin.get("data").toString();
            JSONArray object = JSONObject.parseArray(data);
            json.setData(object);
        } catch (Exception e) {
            e.printStackTrace();
            json.setCode("500");
            json.setMsg("查询失败");
        }

        return json;

    }

    //已验证
    @RequiresPermissions("1")
    @GetMapping("/getIndustry.do")
    public JsonResult<JSONArray> getIndustry() {
        JsonResult<JSONArray> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("查询成功");
        try {
            JSONObject industry = stockService.getIndustry();
            String data = industry.get("data").toString();
            JSONArray object = JSONObject.parseArray(data);
            json.setData(object);
        } catch (Exception e) {
            e.printStackTrace();
            json.setCode("500");
            json.setMsg("查询失败");
        }

        return json;
    }

    //已验证
    @RequiresPermissions("1")
    @PostMapping("/getStockRank.do")
    public JsonResult<JSONObject> getStockRank(@RequestBody JSONObject jsonObject) {
        JsonResult<JSONObject> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("查询成功");
        try {
            JSONObject rank = stockService.getStockRank(jsonObject);
            String data = rank.get("data").toString();
            JSONObject object = JSONObject.parseObject(data);
            json.setData(object);
        } catch (IOException e) {
            e.printStackTrace();
            json.setCode("500");
            json.setMsg("查询失败");
        }

        return json;

    }

    /**
     * 自选
     */
    @RequiresPermissions("1")
    @PutMapping("/pickStock.do")
    public JsonResult<String> pickStock(@PathParam("code") String code){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        pickedStockService.add(user.getId(), code);

        JsonResult<String> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("自选成功");

        return json;
    }

    /**
     * 取消选定
     * @param code
     * @return
     */
    @RequiresPermissions("1")
    @PutMapping("/disPickStock.do")
    public JsonResult<String> disPickStock(@PathParam("code") String code){
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        pickedStockService.delete(user.getId(),code);

        JsonResult<String> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("取消成功");

        return json;
    }


    /**
     * 购买股票
     */
    @RequiresPermissions("1")
    @PutMapping("/buyStock.do")
    public JsonResult<String> buyStock(@RequestBody StockDTO stockDTO){
        JsonResult<String> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("购买成功");

        //先检查钱包
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        double money = walletService.getMoney(user.getId());

        String code = stockDTO.getCode();

        //判断code是否存在
        if (stockAllCacheService.isExist(code)){
            //从网上获取最新信息
            try {
                JSONObject jsonObject = stockService.getStock(code);
                Stock stock = JsonTO.toStock(jsonObject);
                stockInfoCacheService.updateStock(stock.getCode(),stock);

               double price = Double.valueOf(stock.getPrice());

               double total = price * stockDTO.getNum();

               if (money<total){//钱不够
                   json.setCode("403");
                   json.setMsg("购买失败,请及时充值");
               }else {

                   /**
                    * 后期改进 使用事务进行
                    */
                   //扣钱成功
                    if (walletService.remove(user.getId(),total)){
                        buyedStockService.buy(user.getId(),code,stockDTO.getNum());
                    }else{//扣钱失败
                        json.setCode("500");
                        json.setMsg("系统故障，扣钱失败");
                    }


               }

            } catch (IOException e) {
                e.printStackTrace();
                json.setCode("500");
                json.setMsg("购买失败");
            }

        }else {
            json.setCode("403");
            json.setMsg("购买的股票代码不存在");
        }
        return json;
    }

    /**
     * 出售股票
     */
    @RequiresPermissions("1")
    @PutMapping("/sellStock.do")
    public JsonResult<String> sellStock(@RequestBody StockDTO stockDTO){
        JsonResult<String> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("出售成功");

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        int userid = user.getId();
        String code = stockDTO.getCode();

        //查看该股票自己是否有
        BuyedStock buyedStock = buyedStockService.selectBy(userid, code);
        if (buyedStock == null){
            json.setCode("403");
            json.setMsg("你未购买该股票");
        }else{//有
            int num = stockDTO.getNum();
            JSONObject jsonObject = null;

                if (num <= buyedStock.getNum()){
                    try {
                        jsonObject = stockService.getStock(code);
                        Stock stock = JsonTO.toStock(jsonObject);
                        stockInfoCacheService.updateStock(stock.getCode(),stock);
                        double price = Double.valueOf(stock.getPrice());

                        if (walletService.saving(userid,price*num)){
                            num = buyedStock.getNum() - num;
                            buyedStock.setNum(num);
                            buyedStockService.sell(buyedStock);
                        }else {
                            json.setCode("500");
                            json.setMsg("系统故障,出售失败");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        json.setCode("500");
                        json.setMsg("系统故障,出售失败");
                    }

                }else{//数量不够
                    json.setCode("403");
                    json.setMsg("持有数量不足");
                }

        }
        return json;
    }


}
