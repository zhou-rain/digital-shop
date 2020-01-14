package com.bat.qmall.order;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *	8885/order
 */
@SpringBootApplication
@EnableDubbo
public class ShopOrderWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopOrderWebApplication.class, args);
	}

}
