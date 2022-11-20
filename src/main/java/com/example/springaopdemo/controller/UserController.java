package com.example.springaopdemo.controller;


import com.example.springaopdemo.annotation.PreAuthorize;
import com.example.springaopdemo.entity.User;
import com.example.springaopdemo.exception.ValidateException;
import com.example.springaopdemo.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userController")
public class UserController {

    @Resource
    private IUserService userService;
    //todo 现在aop切面里定义的权限数据是集合里写死的，后期可以在数据库里面去定义用户角色表来控制接口权限
    @PreAuthorize("tang")
    @GetMapping("/getUserInfo")
    public Map<String,Object> getUserInfo(@RequestParam("id") String id) throws ValidateException {
        User userInfo=userService.getUserInfo(id);
        HashMap<String,Object> map=new HashMap<>();
        map.put("userInfo",userInfo);
        return map;
    }
}
