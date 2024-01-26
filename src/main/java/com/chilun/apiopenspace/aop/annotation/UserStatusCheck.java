package com.chilun.apiopenspace.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 齿轮
 * @date 2024-01-26-11:47
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserStatusCheck {

    //必须处于某个状态
    int[] mustStatus() default {0};
}