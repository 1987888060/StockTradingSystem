package jsu.per.system.service.impl;

import jsu.per.system.service.UserTokenService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "userToken")
public class UserTokenServiceImpl implements UserTokenService{


    @Override
    @Cacheable(key = "#token")
    public String isExistKey(String token,String str) {
        return str;
    }

    @Override
    @CachePut(key = "#token")
    public String updateToken(String token, String user_id) {
        return user_id;
    }

    @Override
    @CacheEvict(key = "#token")
    public void deleteToken(String token) {
    }

    @Override
    @Cacheable(key = "#token")
    public String addToken(String token, String user_id) {
        return user_id;
    }
}
