package com.bat.shop.api.service.ums;

import com.bat.shop.api.bean.ums.UmsMember;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/13 - 16:57
 * @function:
 */
public interface UmsMemberService {

	UmsMember selectById(int userId);

	List<UmsMember> selectAll();

	/**
	 * 用户登录
	 * @param umsMember
	 * @return
	 */
	UmsMember login(UmsMember umsMember);

	/**
	 * 根据用户id  将token存入redis
	 * @param token
	 * @param memberId
	 */
	void addMemberTokenToRedisByMemberId(String token, Integer memberId);
}
