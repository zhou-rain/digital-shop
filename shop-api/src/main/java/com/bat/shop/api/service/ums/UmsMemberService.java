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

}
