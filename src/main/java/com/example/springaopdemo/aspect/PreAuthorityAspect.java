package com.example.springaopdemo.aspect;

import com.example.springaopdemo.annotation.PreAuthorize;
import com.example.springaopdemo.entity.User;
import com.example.springaopdemo.exception.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component("ss")
public class PreAuthorityAspect {


    private static final String USER_WANG="wang";

    /**
     * 配置切入点表达式。@Before前置通知, 在方法执行之前执行
     * @param joinPoint
     */
    @Before("@annotation(com.example.springaopdemo.annotation.PreAuthorize)")
    public void hasPermi(JoinPoint joinPoint) throws ValidateException {
        HttpServletRequest request=getRequest();
        log.info("请求地址：{}",request.getRequestURI());
        // 请求头有网关标识，标识内部调用（走网关）,进行权限校验（没有配置网关，省略)
        //验证数据权限
            hasPermi(joinPoint,request);
    }

    /**
     * 接口权限校验
     * @param joinPoint
     * @param request
     */
    private void hasPermi(JoinPoint joinPoint, HttpServletRequest request) throws ValidateException {
        List<User> userList=getUserInfo(USER_WANG);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);
        //获取权限标记的value
        String value = preAuthorize.value();
        log.info("权限标识：{},用户名：{},所拥有的角色：{}",value,USER_WANG,userList);

        /*  (省略）
         * 根据自定义注解权限标识 获取用户是否有权限（自定义实现）。
         * 由于我们是微服务项目，所以此处我们feign调用系统管理服务，获取权限信息
         */
        if(userList==null||!userList.contains(value)){
            log.info("无接口权限");
            //原本得是自定义抛出异常（省略了自定义异常）
            throw new ValidateException("没有权限调用接口");
        }
    }

    private List<User> getUserInfo(String userName) {
        //模拟数据库查出来的用户所对应的角色
        User user1=new User("1","wang","001","管理员");
        User user2=new User("2","zhang","002","财务");
        User user3=new User("3","li","003","运营");
        User user4=new User("4","zhou","004","业务员");
        User user5=new User("5","wang","004","预算员");
        List<User> userList=new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);

        //找出传参过来的用户名对应所拥有的角色的集合，然后返回
        List<User> list = userList.stream().filter(user -> user.getUsername().equals(userName)).collect(Collectors.toList());

        return list;
    }

    private HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra=(ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }
}
