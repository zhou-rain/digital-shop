package com.bat.qmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *   search.qmall.com:8887/index
 */
@SpringBootApplication
@EnableDubbo
public class ShopSearchWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSearchWebApplication.class, args);
    }

}
