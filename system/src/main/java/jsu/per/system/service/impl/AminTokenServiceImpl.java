package jsu.per.system.service.impl;

import jsu.per.system.service.AdminTokenService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "adminToken")
public class AminTokenServiceImpl implements AdminTokenService {
    @Override
    @Cacheable(key = "#token")
    public String isExistKey(String token,String str) {
        return str;
    }

    @Override
    @CachePut(key = "#token")
    public String updateToken(String token, String admin_id) {
        return admin_id;
    }

    @Override
    @CacheEvict(key = "#token")
    public void deleteToken(String token) {
    }

    @Override
    @Cacheable(key = "#token")
    public String addToken(String token, String admin_id) {
        return admin_id;
    }
}
