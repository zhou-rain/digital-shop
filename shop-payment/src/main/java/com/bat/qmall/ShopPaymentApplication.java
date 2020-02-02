package com.bat.qmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * payment.qmall.com:8881
 */
@SpringBootApplication
@EnableDubbo
@MapperScan("com.bat.shop.api.mapper")
public class ShopPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopPaymentApplication.class, args);
	}

}
