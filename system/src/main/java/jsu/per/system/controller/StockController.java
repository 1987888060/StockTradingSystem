package jsu.per.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jsu.per.system.pojo.Stock;
import jsu.per.system.result.JsonResult;
import jsu.per.system.service.StockAllCacheService;
import jsu.per.system.service.StockInfoCacheService;
import jsu.per.system.service.StockService;
import jsu.per.system.utils.JsonTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        //线程缓存里面查询
        Stock stock = stockInfoCacheService.getStock(code);
        if (stock == null){//不存在
            stockInfoCacheService.deleteStock(code);
            try {
                JSONObject jsonObject = stockService.getStock(code);
                stock = JsonTO.toStock(jsonObject);
                //更新到缓存中去
                stockInfoCacheService.updateStock(stock.getCode(),stock);
                json.setData(stock);
            } catch (Exception e) {
                json.setCode("500");
                json.setMsg("查询失败");
            }

        }else{
            json.setData(stock);
        }
        return json;
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

    @RequiresPermissions("1")
    @GetMapping("/getAllIndex.do")
    public JsonResult<JSONObject> getAllIndex() {
        JsonResult<JSONObject> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("查询成功");
        try {
            JSONObject stockMin = stockService.getAllIndex();
            String data = stockMin.get("data").toString();
            JSONObject object = JSONObject.parseObject(data);
            json.setData(object);
        } catch (Exception e) {
            e.printStackTrace();
            json.setCode("500");
            json.setMsg("查询失败");
        }

        return json;

    }

    @RequiresPermissions("1")
    @GetMapping("/getIndustry.do")
    public JsonResult<JSONObject> getIndustry() {
        JsonResult<JSONObject> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("查询成功");
        try {
            JSONObject stockMin = stockService.getIndustry();
            String data = stockMin.get("data").toString();
            JSONObject object = JSONObject.parseObject(data);
            json.setData(object);
        } catch (Exception e) {
            e.printStackTrace();
            json.setCode("500");
            json.setMsg("查询失败");
        }

        return json;

    }

    @RequiresPermissions("1")
    @GetMapping("/getStockRank.do")
    public JsonResult<JSONObject> getStockRank(@RequestBody JSONObject jsonObject) {
        JsonResult<JSONObject> json = new JsonResult<>();
        json.setCode("200");
        json.setMsg("查询成功");
        try {
            JSONObject stockMin = stockService.getStockRank(jsonObject);
            String data = stockMin.get("data").toString();
            JSONObject object = JSONObject.parseObject(data);
            json.setData(object);
        } catch (IOException e) {
            e.printStackTrace();
            json.setCode("500");
            json.setMsg("查询失败");
        }

        return json;

    }
}
