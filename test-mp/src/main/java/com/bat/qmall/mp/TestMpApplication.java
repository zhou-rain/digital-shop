package com.bat.qmall.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bat.qmall.mp.mapper")
public class TestMpApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestMpApplication.class, args);
	}

}
