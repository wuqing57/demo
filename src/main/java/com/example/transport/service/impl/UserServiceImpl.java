package com.example.transport.service.impl;

import com.example.transport.bean.User;
import com.example.transport.bean.UserInfo;
import com.example.transport.dao.UserMapper;
import com.example.transport.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAll() {
        PageHelper.startPage(2,1);
        List<User> list = userMapper.getAll();
        return list;
    }

    @Override
    public List<User> getUserId(String userId) {
        List<User> list = userMapper.getUserId(userId);
        return list;
    }

    @Override
    public List<UserInfo> getUserId2(String userId) {
        List<UserInfo> list = userMapper.getUserId2(userId);
        return list;
    }

    @Override
    public List<User> getName(String name) {
        List<User> list = userMapper.getName(name);
        return list;
    }

    @Override
    public void add(User user) {
        userMapper.add(user.getName(),user.getPassword(),user.getUserId(),user.getEmail(),user.getAdmin());
    }

    @Override
    public List<User> getUser(String name, String password) {
        return userMapper.getUser(name, password);
    }
}
