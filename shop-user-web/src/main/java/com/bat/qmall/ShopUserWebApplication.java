package com.bat.qmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 8886/user
 */
@SpringBootApplication
@EnableDubbo
public class ShopUserWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopUserWebApplication.class, args);
    }

}
