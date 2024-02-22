package com.chilun.apiopenspace.model.dto.User;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-01-25-19:53
 */
@Data
public class UserLoginRequest {
    @NotNull
    private String userAccount;
    @NotNull
    private String userPassword;
}
