package com.bat.qmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *   cart.qmall.com:8884/cartList
 */
@SpringBootApplication
@EnableDubbo
public class ShopCartWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopCartWebApplication.class, args);
	}

}
