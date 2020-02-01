package com.bat.qmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *	order.qmall.com:8885
 */
@SpringBootApplication
@EnableDubbo
public class ShopOrderWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopOrderWebApplication.class, args);
	}

}
