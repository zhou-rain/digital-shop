package com.bat.qmall.search;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bat.shop.api.mapper")
@EnableDubbo
public class ShopSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSearchServiceApplication.class, args);
    }

}
