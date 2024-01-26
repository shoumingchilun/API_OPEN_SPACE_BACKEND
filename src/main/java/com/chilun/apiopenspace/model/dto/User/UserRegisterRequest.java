package com.chilun.apiopenspace.model.dto.User;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 齿轮
 * @date 2024-01-25-19:02
 */
@Data
public class UserRegisterRequest implements Serializable {
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
