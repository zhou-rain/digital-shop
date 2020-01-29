package com.bat.qmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  userpass.qmall.com:8882
 */
@SpringBootApplication
@EnableDubbo
public class ShopUserpassWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopUserpassWebApplication.class, args);
	}

}
