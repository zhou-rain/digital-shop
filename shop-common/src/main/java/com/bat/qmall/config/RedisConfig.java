package com.bat.qmall.config;

import com.bat.shop.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhouR
 * @date: Create in 2020/1/19 - 12:23
 * @function:
 */
@Configuration
public class RedisConfig {

	//读取配置文件中的redis的ip地址
	@Value("${spring.redis.host:disabled}")
	private String host;

	@Value("${spring.redis.port:6379}")
	private int port;

	@Value("${spring.redis.database:0}")
	private int database;

	@Bean
	public RedisUtil getRedisUtil() {
		if (host.equals("disabled")) {
			return null;
		}
		RedisUtil redisUtil = new RedisUtil();
		redisUtil.initPool(host, port, database);
		return redisUtil;
	}
}