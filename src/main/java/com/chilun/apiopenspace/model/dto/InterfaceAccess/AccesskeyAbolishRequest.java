package com.chilun.apiopenspace.model.dto.InterfaceAccess;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-02-16-12:18
 */
@Data
public class AccesskeyAbolishRequest {
    @NotNull
    @Schema(description = "接口访问码")
    private String accesskey;
}
