package com.example.transport.service;

import com.example.transport.bean.User;
import com.example.transport.bean.UserInfo;

import java.util.Collection;
import java.util.List;

public interface UserService {
    List<User> getAll();

    List<User> getUserId(String userId);

    List<UserInfo> getUserId2(String userId);

    List<User> getName(String name);

    void add(User user);

    List<User> getUser(String name, String password);
}

