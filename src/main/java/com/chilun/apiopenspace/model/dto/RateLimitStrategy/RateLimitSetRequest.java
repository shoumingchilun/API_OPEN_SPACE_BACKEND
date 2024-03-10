package com.chilun.apiopenspace.model.dto.RateLimitStrategy;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-03-10-17:48
 */
@Data
public class RateLimitSetRequest {
    @NotNull
    Long id;

    @NotNull
    private Integer replenishRate;

    @NotNull
    private Integer burstCapacity;

    @NotNull
    private Integer requestedTokens;
}
