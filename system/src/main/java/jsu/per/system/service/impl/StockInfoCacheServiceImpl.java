package jsu.per.system.service.impl;

import jsu.per.system.pojo.Stock;
import jsu.per.system.service.StockInfoCacheService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "stock:info")
public class StockInfoCacheServiceImpl implements StockInfoCacheService {
    @Override
    @Cacheable(key = "#code")
    public Stock addStock(String code, Stock stock) {
        return stock;
    }

    @Override
    @CacheEvict(key = "#code")
    public void deleteStock(String code) {
    }

    @Override
    @CachePut(key = "#code")
    public Stock updateStock(String code, Stock stock) {
        return stock;
    }

    @Override
    @Cacheable(key = "#code")
    public Stock getStock(String code) {
        return null;
    }
}
