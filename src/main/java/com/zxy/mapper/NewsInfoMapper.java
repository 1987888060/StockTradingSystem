package com.zxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxy.entity.NewsInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsInfoMapper extends BaseMapper<NewsInfo> {

    List<NewsInfo> selectAll();
}
