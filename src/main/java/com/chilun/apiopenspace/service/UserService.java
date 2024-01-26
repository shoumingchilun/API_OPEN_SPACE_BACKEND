package com.chilun.apiopenspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chilun.apiopenspace.model.Masked.UserMasked;
import com.chilun.apiopenspace.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 齿轮
* @description 针对表【user(用户表信息)】的数据库操作Service
* @createDate 2024-01-23 20:21:32
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param username   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String username, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param username  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    UserMasked userLogin(String username, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return 当前登录用户
     */
    User getLoggedInUser(HttpServletRequest request);

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request
     * @return
     */
    User getLoggedInUserPermitNull(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(UserMasked user);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    void userLogout(HttpServletRequest request);

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    UserMasked getUserMasked(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList
     * @return
     */
    List<UserMasked> getUserMasked(List<User> userList);

    /**
     * 密码加密工具方法
     *
     * @param password
     * @return
     */
    String passwordDigest(String password);
}
