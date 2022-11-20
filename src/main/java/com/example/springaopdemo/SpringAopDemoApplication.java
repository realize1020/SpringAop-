package com.example.springaopdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.example.springaopdemo.mapper")
@SpringBootApplication
public class SpringAopDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAopDemoApplication.class, args);
    }

}
