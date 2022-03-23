package jsu.per.system.service.impl;

import jsu.per.system.auth.TokenGenerator;
import jsu.per.system.pojo.UserToken;
import jsu.per.system.service.UserTokenService;
import jsu.per.system.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "usertoken")
public class UserTokenServiceImpl implements UserTokenService {

    /**
     * 添加userToken
     * @param user_id
     */
    @Override
    @Cacheable(key = "#user_id")
    public String addToken(String user_id,String token) {
        return token;
    }


    @Override
    public UserToken getToken(String user_id) {
        return null;
    }

    @Override
    public UserToken updateToken(UserToken token) {
        return null;
    }

    /**
     * 清楚缓存
     * @param user_id
     */
    @Override
    @CacheEvict(key = "#user_id")
    public void deleteToken(String user_id) {
    }

    @Override
    public boolean isExistKey(String user_id) {
        return false;
    }

}
