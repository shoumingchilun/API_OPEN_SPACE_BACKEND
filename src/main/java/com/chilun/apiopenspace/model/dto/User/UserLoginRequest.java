package com.chilun.apiopenspace.model.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-01-25-19:53
 */
@Data
public class UserLoginRequest {
    @NotNull
    @Schema(description = "用户账户")
    private String userAccount;
    @NotNull
    @Schema(description = "用户密码")
    private String userPassword;
}
