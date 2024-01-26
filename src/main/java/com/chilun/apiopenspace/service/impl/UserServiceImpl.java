package com.chilun.apiopenspace.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.constant.SessionKey;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.mapper.UserMapper;
import com.chilun.apiopenspace.model.Masked.UserMasked;
import com.chilun.apiopenspace.model.entity.User;
import com.chilun.apiopenspace.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 齿轮
 * @description 针对表【user(用户表信息)】的数据库操作Service实现
 * @createDate 2024-01-23 20:21:32
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private static final String SALT = "chilun20000002";

    @Override
    public long userRegister(String username, String userPassword, String checkPassword) {
        //一、校验数据格式
        //1是否为空
        ThrowUtils.throwIf(StringUtils.isAnyBlank(username, userPassword, checkPassword), ErrorCode.PARAMS_ERROR, "账号、密码、校验密码为空！");
        //2长度
        ThrowUtils.throwIf(username.length() < 4 || username.length() > 30, ErrorCode.PARAMS_ERROR, "账号长度错误");
        ThrowUtils.throwIf(userPassword.length() < 8 || userPassword.length() > 30, ErrorCode.PARAMS_ERROR, "密码长度错误");
        //3密码和校验密码是否一致
        ThrowUtils.throwIf(!userPassword.equals(checkPassword), ErrorCode.PARAMS_ERROR, "密码与校验密码不一致");
        //二、检查是否已注册
        ThrowUtils.throwIf(getBaseMapper().selectCount(new QueryWrapper<User>().eq("username", username)) != 0,
                ErrorCode.PARAMS_ERROR, "账号已存在");
        //三、插入注册用户记录
        //1加密
        String encryptedUserPassword = passwordDigest(userPassword);
        //2插入
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptedUserPassword);
        ThrowUtils.throwIf(!save(user), ErrorCode.SYSTEM_ERROR, "注册失败");
        //四、返回user的id
        return user.getId();
    }

    @Override
    public UserMasked userLogin(String username, String userPassword, HttpServletRequest request) {
        //一、校验数据格式
        //1是否为空
        ThrowUtils.throwIf(StringUtils.isAnyBlank(username, userPassword), ErrorCode.PARAMS_ERROR, "账号、密码为空！");
        //2长度
        ThrowUtils.throwIf(username.length() < 4 || username.length() > 30, ErrorCode.PARAMS_ERROR, "账号长度错误");
        ThrowUtils.throwIf(userPassword.length() < 8 || userPassword.length() > 30, ErrorCode.PARAMS_ERROR, "密码长度错误");
        //二、加密
        String encryptedUserPassword = passwordDigest(userPassword);
        //三、验证账号
        User user = getOne(new QueryWrapper<User>()
                .eq("username", username)
                .eq("password", encryptedUserPassword));
        ThrowUtils.throwIf(user == null, ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        //四、脱敏
        UserMasked userMasked = getUserMasked(user);
        //五、记录用户态
        request.getSession().setAttribute(SessionKey.USER_IN_SESSION_KEY, userMasked);
        //六、返回
        return userMasked;
    }

    @Override
    public User getLoggedInUser(HttpServletRequest request) {
        //一、判断是否已登录
        Object userObject = request.getSession().getAttribute(SessionKey.USER_IN_SESSION_KEY);
        UserMasked tempUser = (UserMasked) userObject;
        ThrowUtils.throwIf(tempUser == null || tempUser.getId() == null,
                ErrorCode.NOT_LOGIN_ERROR,
                "未登录！");
        //二、根据id查询User并返回
        User user = getById(tempUser.getId());
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR, "该用户不存在");
        return user;
    }

    @Override
    public User getLoggedInUserPermitNull(HttpServletRequest request) {
        //一、判断是否已登录
        Object userObject = request.getSession().getAttribute(SessionKey.USER_IN_SESSION_KEY);
        UserMasked tempUser = (UserMasked) userObject;
        if (tempUser == null || tempUser.getId() == null)
            return null;
        //二、根据id查询User并返回
        return getById(tempUser.getId());
    }

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        //一、获得用户
        UserMasked userMasked = (UserMasked) request.getSession().getAttribute(SessionKey.USER_IN_SESSION_KEY);
        //二、判别角色
        if (userMasked == null)
            return false;
        return isAdmin(userMasked);
    }

    @Override
    public boolean isAdmin(User user) {
        //判断user的role是否为admin
        Integer role = user.getRole();
        return role != null && role == 1;
    }

    public boolean isAdmin(UserMasked user) {
        //判断user的role是否为admin
        Integer role = user.getRole();
        return role != null && role == 1;
    }

    @Override
    public void userLogout(HttpServletRequest request) {
        //1判断有无登录
        ThrowUtils.throwIf(request.getSession().getAttribute(SessionKey.USER_IN_SESSION_KEY) == null,
                ErrorCode.NOT_LOGIN_ERROR,
                "未登录！");
        //2去除session内属性
        request.getSession().removeAttribute(SessionKey.USER_IN_SESSION_KEY);
    }

    @Override
    public UserMasked getUserMasked(User user) {
        UserMasked userMasked = new UserMasked();
        userMasked.setId(user.getId());
        userMasked.setUsername(user.getUsername());
        userMasked.setUserNickname(user.getUserNickname());
        userMasked.setIntroduction(user.getIntroduction());
        userMasked.setRole(user.getRole());
        userMasked.setStatus(user.getStatus());
        userMasked.setTotalBalance(user.getTotalBalance());
        userMasked.setUnusedBalance(user.getUnusedBalance());
        return userMasked;
    }

    @Override
    public List<UserMasked> getUserMasked(List<User> userList) {
        List<UserMasked> userMaskedList = new ArrayList<>();
        for (User user : userList) {
            userMaskedList.add(getUserMasked(user));
        }
        return userMaskedList;
    }

    //加密方法，可用于管理是否开启加密
    @Override
    public String passwordDigest(String password) {
        return DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));
    }
}