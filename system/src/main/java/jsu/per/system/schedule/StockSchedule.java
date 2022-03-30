package jsu.per.system.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jsu.per.system.service.StockAllCacheService;
import jsu.per.system.service.StockService;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StockSchedule {
    private StockService stockService;
    private StockAllCacheService stockAllCacheService;

    @Lazy
    public StockSchedule(StockService stockService, StockAllCacheService stockAllCacheService){

        this.stockService = stockService;
        this.stockAllCacheService = stockAllCacheService;
    }


    //定时异步获取所有的股票 每天0点和12点获取
    @Async
    @Scheduled(cron = "0 0 0/12 * * ?")
    public void getAllStock() throws IOException {
        JSONObject json = stockService.getAllStock();
        String data = json.get("data").toString();
        JSONArray array = JSONObject.parseArray(data);
        int len = array.size();
        for (int i = 0; i < len; i++) {
            String[] split = array.get(i).toString().split(",");
            int a = split[0].length();
            String key = split[0].substring(2,a-1);
            a = split[1].length();
            String value = split[1].substring(1,a-2);
            //以code name 形式存储
            stockAllCacheService.updateStock1(key,value);
            //以name code 形式存储
            stockAllCacheService.updateStock2(value,key);
        }
    }
}
