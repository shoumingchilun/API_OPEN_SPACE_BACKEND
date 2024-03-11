package com.chilun.apiopenspace.model.dto.InterfaceAccess;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 齿轮
 * @date 2024-03-08-19:22
 */
@Data
public class TopUpRequest {
    @NotNull
    @Schema(description = "接口访问码")
    String accesskey;

    @NotNull
    @DecimalMin("0")
    @Schema(description = "充值金额")
    BigDecimal amount;
}
