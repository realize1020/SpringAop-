package com.example.springaopdemo.service.impl;

import com.example.springaopdemo.entity.User;
import com.example.springaopdemo.mapper.UserMapper;
import com.example.springaopdemo.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserInfo(String id) {
        return userMapper.getUserInfo(id);
    }
}
