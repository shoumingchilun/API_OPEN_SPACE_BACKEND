package com.chilun.apiopenspace.model.dto.InterfaceChargeStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @author 齿轮
 * @date 2024-03-08-15:18
 */
@Data
public class InterfaceChargeSetRequest {
    @NotNull
    @Schema(description = "接口ID")
    Long id;

    @NotNull
    @Max(2)
    @Min(0)
    @Schema(description = "计费类型")
    Integer costType;

    @DecimalMax("1000")
    @DecimalMin("0")
    @Schema(description = "固定费用")
    BigDecimal fixedFee;

    @Max(15552000L)
    @Min(1L)
    @Schema(description = "固定费用生效时间")
    Long fixedTime;
}
