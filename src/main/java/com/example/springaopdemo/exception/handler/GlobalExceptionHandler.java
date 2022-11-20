package com.example.springaopdemo.exception.handler;


import com.example.springaopdemo.exception.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    //没有定义统一返回结果类，所以用map代替
    public Map<String,Object> handleException(Exception e) {
        String errorMsg = "";
        if (e instanceof NullPointerException) {
            errorMsg = "参数空指针异常";
        } else if (e instanceof ValidateException) {
            errorMsg = "接口异常," + e.getMessage();
        } else {
            errorMsg = e.getMessage();
        }
        log.error(String.format("请求异常[%s]", e));

        HashMap<String,Object> map = new HashMap<>();
        map.put("400",errorMsg);
        return map;
    }

}
