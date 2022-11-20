package com.example.springaopdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    //用户Id
    private String id;

    //用户名
    private String username;

    //角色id
    private String roleId;

    //角色名称
    private String roleName;
}
