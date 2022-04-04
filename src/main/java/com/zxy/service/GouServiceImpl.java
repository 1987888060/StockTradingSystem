package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.entity.Gou;
import com.zxy.mapper.GouMapper;
import org.springframework.stereotype.Service;

@Service
public class GouServiceImpl extends ServiceImpl<GouMapper,Gou> implements GouService{
}
