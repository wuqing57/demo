package com.example.transport.dao;

import com.example.transport.bean.User;
import com.example.transport.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {
    List<User> getAll();

    List<User> getUserId(String userId);

    List<UserInfo> getUserId2(String userId);

    List<User> getName(String name);

    void add(String name, String password, String userId, String email, int admin);


    List<User> getUser(String name, String password);
}
