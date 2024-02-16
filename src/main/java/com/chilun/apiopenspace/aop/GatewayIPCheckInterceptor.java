package com.chilun.apiopenspace.aop;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.aop.annotation.GatewayIPCheck;
import com.chilun.apiopenspace.constant.NacosServiceName;
import com.chilun.apiopenspace.exception.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 齿轮
 * @date 2024-02-16-14:34
 */
@Aspect
@Component
public class GatewayIPCheckInterceptor {
    @Resource
    NacosServiceDiscovery nacosServiceDiscovery;

    //执行拦截
    @Around("@annotation(ipCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, GatewayIPCheck ipCheck) throws Throwable {
        //一、获得所需权限
        List<String> fromIP = new ArrayList<>();
        nacosServiceDiscovery.getInstances(NacosServiceName.GATEWAY_NAME).forEach(instance -> {
            fromIP.add(instance.getHost());
        });
        //二、获得请求来源ip
        //1获得当前请求
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 2获得请求ip
        // 获取请求的IP地址
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        //三、进行鉴权
        ThrowUtils.throwIf(!fromIP.contains(ipAddress), ErrorCode.NO_AUTH_ERROR, "来源异常，禁止操作！");
        // 四、通过权限校验，放行
        return joinPoint.proceed();
    }
}
