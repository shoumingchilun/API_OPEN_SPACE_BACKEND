package com.chilun.apiopenspace.model.dto.InterfaceInfo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-01-27-23:10
 */
@Data
public class InterfaceRegisterRequest {

    @NotNull
    @Length(max = 200,min = 1)
    private String requestPath;

    @Min(0)
    @Max(8)
    @NotNull
    private Integer requestMethod;
}
