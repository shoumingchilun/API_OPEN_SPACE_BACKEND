package com.chilun.apiopenspace.constant;

/**
 * @author 齿轮
 * @date 2024-01-26-11:34
 * 角色状态常量
 * 0-正常/1-禁止发布接口/2-禁止访问接口/3-停用（封禁）
 */
public interface UserStatusValue {
    //正常
    int NORMAL = 0;

    //禁止发布接口
    int DIS_PUBLISH = 1;

    //禁止访问接口
    int DIS_ACCESS = 2;

    //封禁
    int BAN = 3;
}
