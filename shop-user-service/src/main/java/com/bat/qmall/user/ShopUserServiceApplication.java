package com.bat.qmall.user;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bat.shop.api.mapper")
@EnableDubbo
public class ShopUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopUserServiceApplication.class, args);
    }

}
