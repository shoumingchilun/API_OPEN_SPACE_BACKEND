package com.chilun.apiopenspace.model.dto.InterfaceAccess;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-02-16-12:51
 */
@Data
public class ChangeVerifyRequest {

    @NotNull
    private String accesskey;

    @NotNull
    @Min(0)
    @Max(1)
    private Integer type;
}
