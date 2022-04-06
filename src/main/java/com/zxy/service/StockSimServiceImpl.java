package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.entity.StockSim;
import com.zxy.mapper.StockSimMapper;
import org.springframework.stereotype.Service;

@Service
public class StockSimServiceImpl extends ServiceImpl<StockSimMapper, StockSim> implements StockSimService{
}
