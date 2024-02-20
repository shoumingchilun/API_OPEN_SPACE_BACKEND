package com.chilun.apiopenspace.model.dto.User;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 齿轮
 * @date 2024-01-26-16:55
 */
@Data
public class UserSelfUpdateRequest {
    @Length(min = 8, max = 30)
    private String password;

    @Length(min = 1, max = 16)
    private String userNickname;

    @Length(max = 1000)
    private String introduction;
}
