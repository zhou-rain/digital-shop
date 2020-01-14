package com.bat.qmall.user.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bat.shop.api.bean.ums.UmsMember;
import com.bat.shop.api.mapper.ums.UmsMemberMapper;
import com.bat.shop.api.service.ums.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/13 16:04
 */
@Service
@Component
public class UserServiceImpl implements UmsMemberService {

    @Autowired
    UmsMemberMapper umsMemberMapper;


    @Override
    public UmsMember selectById(int i) {
        return umsMemberMapper.selectById(i);
    }

    @Override
    public List<UmsMember> selectAll() {
        return umsMemberMapper.selectList(null);
    }
}
