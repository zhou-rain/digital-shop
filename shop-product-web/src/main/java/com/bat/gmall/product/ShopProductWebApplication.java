package com.bat.gmall.product;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 8888/product
 */
@SpringBootApplication
@EnableDubbo
public class ShopProductWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopProductWebApplication.class, args);
    }

}
