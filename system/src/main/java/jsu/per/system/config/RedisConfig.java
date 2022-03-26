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
                //设置过期时间 2分钟
                .entryTtl(Duration.ofMinutes(2))
                .serializeKeysWith(keyPair())
                .serializeValuesWith(valuePair());
        return RedisCacheManager.builder(factory)
                .withCacheConfiguration("userToken",configuration1)
                .withCacheConfiguration("vCode",configuration2)
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
