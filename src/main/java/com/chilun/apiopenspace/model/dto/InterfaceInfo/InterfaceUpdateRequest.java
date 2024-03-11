package com.chilun.apiopenspace.model.dto.InterfaceInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-01-27-23:37
 */
@Data
public class InterfaceUpdateRequest {
    @NotNull
    @Schema(description = "接口id")
    private Long id;

    @Length(max = 200,min = 1)
    @Schema(description = "接口请求路径")
    private String requestPath;

    @Max(8)
    @Min(0)
    @Schema(description = "接口请求方法")
    private Integer requestMethod;

    @Length(max = 250)
    @Schema(description = "接口请求数据类型")
    private String requestDataType;

    @Length(max = 1000)
    @Schema(description = "接口请求参数")
    private String requestParameters;

    @Length(max = 1000)
    @Schema(description = "接口响应示例")
    private String requestExample;

    @Length(max = 1000)
    @Schema(description = "接口响应状态")
    private String responseStatus;

    @Length(max = 1000)
    @Schema(description = "接口响应参数")
    private String responseParameters;

    @Length(max = 1000)
    @Schema(description = "接口响应状态码")
    private String responseExample;

    @Max(1)
    @Min(0)
    @Schema(description = "接口状态")
    private Integer status;

    @Length(max = 250)
    @Schema(description = "接口简介")
    private String introduction;

    @Max(1)
    @Min(0)
    @Schema(description = "接口是否计费")
    private Integer isCost;

    @Max(1)
    @Min(0)
    @Schema(description = "接口是否有请求限制")
    private Integer isRestrict;

    @Max(1)
    @Min(0)
    @Schema(description = "接口是否有异常处理")
    private Integer isExceptionHandling;
}
