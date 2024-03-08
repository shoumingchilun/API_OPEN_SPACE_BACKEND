package com.chilun.apiopenspace.model.dto.InterfaceChargeStrategy;

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
    Long id;

    @NotNull
    @Max(2)
    @Min(0)
    Integer costType;

    @DecimalMax("1000")
    @DecimalMin("0")
    BigDecimal fixedFee;

    @Max(15552000L)
    @Min(1L)
    Long fixedTime;
}
