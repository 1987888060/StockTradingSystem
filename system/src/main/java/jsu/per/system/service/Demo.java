package jsu.per.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Demo {
    private StockService stockService;
    private StockAllCacheService stockAllCacheService;

    @Lazy
    public Demo(StockService stockService, StockAllCacheService stockAllCacheService) {

        this.stockService = stockService;
        this.stockAllCacheService = stockAllCacheService;
    }

    public void getAllStock() throws IOException {
        JSONObject json = stockService.getAllStock();
        String data = json.get("data").toString();
        JSONArray array = JSONObject.parseArray(data);
        int len = array.size();
        for (int i = 0; i < len; i++) {
            System.out.println(array.get(i).toString());
            String[] split = array.get(i).toString().split(",");
            int a = split[0].length();
            String key = split[0].substring(2,a-1);
            a = split[1].length();
            String value = split[1].substring(1,a-2);
            System.out.println(key+","+value);
            //以code name 形式存储
            stockAllCacheService.updateStock1(key,value);
            //以name code 形式存储
            stockAllCacheService.updateStock2(value,key);
        }
    }
}
