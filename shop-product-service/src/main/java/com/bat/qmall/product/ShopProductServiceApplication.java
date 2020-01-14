package com.bat.qmall.product;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bat.shop.api.mapper")
@EnableDubbo
public class ShopProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopProductServiceApplication.class, args);
    }

}
