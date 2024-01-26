package com.chilun.apiopenspace.model.dto.User;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 * @author 齿轮
 * @date 2024-01-26-12:27
 */
@Data
public class UserAddRequest {

    @NotEmpty
    @Length(min = 4, max = 30)
    private String username;

    @NotEmpty
    @Length(min = 8, max = 30)
    private String password;

    @Length(max = 16)
    private String userNickname;

    @Length(max = 1000)
    private String introduction;

    @Min(0)
    @Max(1)
    private Integer role;

    @Min(0)
    @Max(3)
    private Integer status;

    @PositiveOrZero
    private BigDecimal totalBalance;

    @PositiveOrZero
    private BigDecimal unusedBalance;
}
