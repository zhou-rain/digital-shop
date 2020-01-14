package com.bat.shop.api.shiro.factory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 11:53
 * @function:
 */
public class ShiroMapFactory {


	public Map<String, String> build() {

		Map<String,String> map = new LinkedHashMap<>();

		/*List<ShopShiro> all = shopShiroMapper.getAll();

		//保存进去是什么顺序，取出来还是什么顺序
		Map<String,String> map = new LinkedHashMap<>();

		for (ShopShiro shiro : all) {
			map.put(shiro.getSname(),shiro.getSfilter());
		}

		*//*map1.put("/favicon.ico","anon");
		map1.put("/static/**","anon");
		map1.put("/images/**","anon");
		map1.put("/css/**","anon");
		map1.put("/js/**","anon");
		map1.put("/shiro/login","anon");
		map1.put("/shiro/logout","logout");
		map1.put("/admin.html","roles[admin]");
		map1.put("/user.html","roles[user,admin]");
		map.put("/**","authc");*//*

		System.out.println("map = " + map);*/
		return map;
	}
}
