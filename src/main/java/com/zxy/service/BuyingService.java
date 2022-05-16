package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.entity.Buying;
import com.zxy.entity.Selling;

import java.util.List;

public interface BuyingService extends IService<Buying> {
    List<Buying> get(String stockcode);
}
