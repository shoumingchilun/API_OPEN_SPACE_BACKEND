package com.chilun.apiopenspace.model.dto.InterfaceAccess;

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
    String accesskey;

    @NotNull
    @DecimalMin("0")
    BigDecimal amount;
}
