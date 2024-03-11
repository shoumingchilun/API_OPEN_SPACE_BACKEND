package com.chilun.apiopenspace.model.dto.InterfaceAccess;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "接口访问码")
    private String accesskey;

    @NotNull
    @Min(0)
    @Max(1)
    @Schema(description = "验证方式")
    private Integer type;
}
