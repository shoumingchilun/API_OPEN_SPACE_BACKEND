package com.chilun.apiopenspace.model.dto.InterfaceInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-01-27-23:18
 */
@Data
public class InterfaceStatusManageRequest {
    @NotNull
    @Schema(description = "接口id")
    private Long id;

    @NotNull
    @Max(1)
    @Min(0)
    @Schema(description = "接口状态")
    private Integer status;
}
