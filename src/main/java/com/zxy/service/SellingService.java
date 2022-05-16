package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.entity.Selling;

import java.util.List;

public interface SellingService extends IService<Selling> {

    List<Selling> get(String stockcode);
}
