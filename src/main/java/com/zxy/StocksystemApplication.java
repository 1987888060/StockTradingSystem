package com.zxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zxy.mapper")
public class StocksystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksystemApplication.class, args);
    }

}
