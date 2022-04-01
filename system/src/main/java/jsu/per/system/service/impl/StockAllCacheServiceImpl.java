package jsu.per.system.service.impl;

import jsu.per.system.pojo.Stock;
import jsu.per.system.service.StockAllCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@CacheConfig
public class StockAllCacheServiceImpl implements StockAllCacheService {
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    @CachePut(cacheNames = "stock:all1",key = "#code")
    public String updateStock1(String code, String name) {
        return name;
    }

    @Override
    @CachePut(cacheNames = "stock:all2",key = "#name")
    public String updateStock2(String name, String code) {
        return code;
    }

    @Override
    public Set<Stock> getKeyValue(String str) {
        Set<Stock> stocks = new HashSet<>();
        //从all1中查询 code name
        String string = "stock:all1::"+str+"*";
        Set<String> key1s = redisTemplate.keys(string);
        for (String key:key1s){
            String value = redisTemplate.opsForValue().get(key);
            Stock stock = new Stock();
            stock.setName(value.substring(1,value.length()-1));
            stock.setCode(key.substring(12));
            stocks.add(stock);
        }
        //先从all2中查询 name code
        string = "stock:all2::"+str+"*";
        Set<String> key2s = redisTemplate.keys(string);
        for (String key:key2s){
            String value = redisTemplate.opsForValue().get(key);
            //String value1 = value.substring(12,-1);
            Stock stock = new Stock();
            stock.setName(key.substring(12));
            stock.setCode(value.substring(1,value.length()-1));
            stocks.add(stock);
        }
        return stocks;
    }

    @Override
    public boolean isExist(String code) {
        String string = "stock:all1::"+code;
        return redisTemplate.hasKey(string);
    }

    @Override
    @Cacheable(cacheNames = "stock:all1",key = "#code")
    public Stock getStock(String code) {
        return null;
    }
}
