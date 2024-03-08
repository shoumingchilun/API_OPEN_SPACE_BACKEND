package com.chilun.apiopenspace.model.dto.InterfaceAccess;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 齿轮
 * @date 2024-02-16-13:21
 */
@Data
public class AccesskeyAddRequest {
    @NotNull
    private Long interfaceId;

    @NotNull
    private Long userid;

    @Max(1)
    @Min(0)
    private Integer verifyType;

    private BigDecimal remainingAmount;

    private BigDecimal cost;

    private Date expiration;

    @PositiveOrZero
    private Integer callTimes;

    @PositiveOrZero
    private Integer failedCallTimes;
}
