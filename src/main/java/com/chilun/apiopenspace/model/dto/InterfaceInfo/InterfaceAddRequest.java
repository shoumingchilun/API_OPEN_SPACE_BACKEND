package com.chilun.apiopenspace.model.dto.InterfaceInfo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-01-28-10:17
 */
@Data
public class InterfaceAddRequest {

    @NotNull
    private Long userid;

    @Length(max = 200,min = 1)
    @NotNull
    private String requestPath;

    @Max(8)
    @Min(0)
    @NotNull
    private Integer requestMethod;

    @Length(max = 250)
    private String requestDataType;

    @Length(max = 1000)
    private String requestParameters;

    @Length(max = 1000)
    private String requestExample;

    @Length(max = 1000)
    private String responseStatus;

    @Length(max = 1000)
    private String responseParameters;

    @Length(max = 1000)
    private String responseExample;

    @Max(1)
    @Min(0)
    private Integer status;

    @Length(max = 250)
    private String introduction;

    @Max(1)
    @Min(0)
    private Integer isCost;

    @Max(1)
    @Min(0)
    private Integer isRestrict;

    @Max(1)
    @Min(0)
    private Integer isExceptionHandling;
}
