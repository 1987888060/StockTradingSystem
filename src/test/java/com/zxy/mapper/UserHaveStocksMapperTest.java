package com.zxy.mapper;

import com.zxy.entity.Stocks;
import com.zxy.service.UserHaveStocksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class UserHaveStocksMapperTest {

    @Autowired
    private UserHaveStocksMapper mapper;

    @Autowired
    private UserHaveStocksService userHaveStocksService;



}