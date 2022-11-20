package com.example.springaopdemo.mapper;

import com.example.springaopdemo.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User getUserInfo(@Param("id") String id);
}
