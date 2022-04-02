package jsu.per.system.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    /**
     * 配置缓存管理器
     * @param factory redis 线程安全连接工厂
     * @return 缓存管理器
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){
        RedisCacheConfiguration configuration1 =RedisCacheConfiguration.defaultCacheConfig()
                //设置过期时间 1小时
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(keyPair())
                .serializeValuesWith(valuePair());
        RedisCacheConfiguration configuration2 = RedisCacheConfiguration.defaultCacheConfig()
                //设置过期时间 5分钟
                .entryTtl(Duration.ofMinutes(5))
                .serializeKeysWith(keyPair())
                .serializeValuesWith(valuePair());
        //所有股票信息 只包含股票名和股票代码 每天的查询两次
        RedisCacheConfiguration configuration3 = RedisCacheConfiguration.defaultCacheConfig()
                //设置过期时间 12小时
                .entryTtl(Duration.ofHours(12))
                .serializeKeysWith(keyPair())
                .serializeValuesWith(valuePair());
        //所有股票信息 只包含股票名和股票代码 每天的查询两次
        RedisCacheConfiguration configuration4 = RedisCacheConfiguration.defaultCacheConfig()
                //设置过期时间 12小时
                .entryTtl(Duration.ofHours(12))
                .serializeKeysWith(keyPair())
                .serializeValuesWith(valuePair());
        //存储用户查询到的股票基本信息
        RedisCacheConfiguration configuration5 = RedisCacheConfiguration.defaultCacheConfig()
                //设置过期时间 1小时
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(keyPair())
                .serializeValuesWith(valuePair());
        //存储admin token
        RedisCacheConfiguration configuration6 = RedisCacheConfiguration.defaultCacheConfig()
                //设置过期时间 1小时
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(keyPair())
                .serializeValuesWith(valuePair());
        return RedisCacheManager.builder(factory)
                .withCacheConfiguration("userToken",configuration1)
                .withCacheConfiguration("vCode",configuration2)
                .withCacheConfiguration("stock:all1",configuration3)
                .withCacheConfiguration("stock:all2",configuration4)
                .withCacheConfiguration("stock:info",configuration5)
                .withCacheConfiguration("adminToken",configuration6)
                .build();
    }


    /**
     * 配置键序列化
     * @return
     */
    private RedisSerializationContext.SerializationPair<String> keyPair(){
        return RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());
    }

    /**
     * 配置值序列化
     * @return
     */
    private RedisSerializationContext.SerializationPair<Object> valuePair(){
        return RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());
    }
}
