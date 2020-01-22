package com.bat.qmall.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhouR
 * @date: Create in 2020/1/22 - 14:34
 * @function:
 */
@Configuration
public class RedissonConfig {

	@Value("${spring.redis.host:disabled}")
	private String host;

	@Value("${spring.redis.port:6379}")
	private int port;

	@Bean
	public RedissonClient redissonClient(){
		Config config = new Config();
		config.useSingleServer().setAddress("redis://"+host+":"+port);
		return Redisson.create(config);
	}


}
