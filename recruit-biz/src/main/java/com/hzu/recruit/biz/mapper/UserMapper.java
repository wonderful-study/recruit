package com.hzu.recruit.biz.mapper;

import com.hzu.recruit.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    List<User> selectUserList();

    //把用户信息存入数据库
    int insert(User user);

    //根据email来删除用户
    int deleteByEmail(String email);

    //根据email更新用户
    int updateByEmail(User updateUser);

    List<User> selectUsersByQuery(User user);
}
