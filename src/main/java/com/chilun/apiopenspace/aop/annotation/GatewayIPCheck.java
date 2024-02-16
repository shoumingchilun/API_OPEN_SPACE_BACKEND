package com.chilun.apiopenspace.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 必须来源于网关的ip地址
 * @author 齿轮
 * @date 2024-02-16-14:33
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GatewayIPCheck {
}
