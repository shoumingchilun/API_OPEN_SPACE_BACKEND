package com.chilun.apiopenspace.aop;

import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.aop.annotation.UserAuthCheck;
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

/**
 * @author 齿轮
 * @date 2024-01-26-11:30
 */
@Aspect
@Component
public class AuthCheckInterceptor {

    @Resource
    private UserService userService;

    //执行拦截
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, UserAuthCheck authCheck) throws Throwable {
        //一、获得所需权限
        int mustRole = authCheck.mustRole();
        //二、获得当前用户
        //1获得当前请求
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 2获得当前登录用户权限
        User loginUser = userService.getLoggedInUser(request);
        ThrowUtils.throwIf(loginUser==null,ErrorCode.NOT_LOGIN_ERROR,"未登录！");
        Integer role = loginUser.getRole();
        ThrowUtils.throwIf(role==null,ErrorCode.NOT_LOGIN_ERROR,"获得用户权限失败");
        //三、进行鉴权
        ThrowUtils.throwIf(role!=mustRole, ErrorCode.NO_AUTH_ERROR,"无权操作！");
        // 四、通过权限校验，放行
        return joinPoint.proceed();
    }
}