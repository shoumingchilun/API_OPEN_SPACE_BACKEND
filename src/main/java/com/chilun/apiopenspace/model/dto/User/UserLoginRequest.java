package com.chilun.apiopenspace.model.dto.User;

import lombok.Data;

/**
 * @author 齿轮
 * @date 2024-01-25-19:53
 */
@Data
public class UserLoginRequest {
    private String userAccount;
    private String userPassword;
}
