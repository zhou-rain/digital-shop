package com.bat.qmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.bean.ums.UmsMember;
import com.bat.shop.api.service.ums.UmsMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/13 16:09
 */
@Controller
public class UserController {


    @Reference(check = false)
    UmsMemberService umsMemberService;

    @RequestMapping("/index")
    public String index(){
        return "index.html";
    }


    @RequestMapping("/ok")
    @ResponseBody
    public List<UmsMember> ok(){
        UmsMember user = umsMemberService.selectById(8);

        List<UmsMember> umsMembers = umsMemberService.selectAll();

        return umsMembers;
    }

}
