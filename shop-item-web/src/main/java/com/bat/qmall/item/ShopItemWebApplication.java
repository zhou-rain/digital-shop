package com.bat.qmall.item;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * item.qmall.com:8883/11.html
 */
@SpringBootApplication
@EnableDubbo
public class ShopItemWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopItemWebApplication.class, args);
	}

}
