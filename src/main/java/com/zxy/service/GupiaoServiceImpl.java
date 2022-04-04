package com.zxy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.entity.Gupiao;
import com.zxy.mapper.GupiaoMapper;
import org.springframework.stereotype.Service;

@Service
public class GupiaoServiceImpl extends ServiceImpl<GupiaoMapper, Gupiao> implements GupiaoService{
}
