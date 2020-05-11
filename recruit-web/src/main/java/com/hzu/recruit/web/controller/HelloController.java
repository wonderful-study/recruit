package com.hzu.recruit.web.controller;

import com.hzu.recruit.biz.mapper.UserMapper;
import com.hzu.recruit.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HelloController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("hello")
    public String hello(ModelMap modelMap) {
        List<User> users = userMapper.selectUserList();
        User user = users.get(2);
        if( user == null) {
            throw new IllegalArgumentException();
        }
        modelMap.put("user",user);
        return "hello";
    }

}
