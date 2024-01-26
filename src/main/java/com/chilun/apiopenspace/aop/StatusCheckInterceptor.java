package com.chilun.apiopenspace.aop;

import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.aop.annotation.UserAuthCheck;
import com.chilun.apiopenspace.aop.annotation.UserStatusCheck;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.model.entity.User;
import com.chilun.apiopenspace.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author 齿轮
 * @date 2024-01-26-11:53
 */
@Aspect
@Component
public class StatusCheckInterceptor {

    @Resource
    private UserService userService;

    //执行拦截
    @Around("@annotation(statusCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, UserStatusCheck statusCheck) throws Throwable {
        //一、获得应处于的状态
        int[] mustStatus = statusCheck.mustStatus();
        //二、获得当前用户
        //1获得当前请求
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 2获得当前登录用户状态
        User loginUser = userService.getLoggedInUser(request);
        ThrowUtils.throwIf(loginUser==null, ErrorCode.NOT_LOGIN_ERROR,"未登录！");
        Integer status = loginUser.getStatus();
        ThrowUtils.throwIf(status==null,ErrorCode.NOT_LOGIN_ERROR,"获得用户状态失败");
        //三、进行鉴权
        ThrowUtils.throwIf(Arrays.asList(mustStatus).contains(status), ErrorCode.NO_AUTH_ERROR,"异常状态禁止操作！");
        // 四、通过状态校验，放行
        return joinPoint.proceed();
    }
}