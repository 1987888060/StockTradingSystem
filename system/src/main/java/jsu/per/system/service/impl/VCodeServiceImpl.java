package jsu.per.system.service.impl;

import jsu.per.system.service.VCodeService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "vCode")
public class VCodeServiceImpl implements VCodeService {

    @Override
    @CachePut(key = "#email")
    public String updateCode(String email, String code) {
        return code;
    }

    @Override
    @Cacheable(key = "#email")
    public String addCode(String email, String code) {
        return code;
    }
}
