package com.chilun.apiopenspace.model.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 齿轮
 * @date 2024-01-25-19:02
 */
@Data
public class UserRegisterRequest implements Serializable {
    @NotNull
    @Length(max = 30, min = 4)
    @Schema(description = "用户账户")
    private String userAccount;

    @NotNull
    @Length(max = 30, min = 8)
    @Schema(description = "用户密码")
    private String userPassword;

    @NotNull
    @Length(max = 30, min = 8)
    @Schema(description = "用户再次输入密码")
    private String checkPassword;
}
