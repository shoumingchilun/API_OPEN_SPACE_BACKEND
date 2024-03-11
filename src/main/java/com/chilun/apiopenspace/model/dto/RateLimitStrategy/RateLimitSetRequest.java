package com.chilun.apiopenspace.model.dto.RateLimitStrategy;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-03-10-17:48
 */
@Data
public class RateLimitSetRequest {
    @NotNull
    @Schema(description = "限流策略id")
    Long id;

    @NotNull
    @Schema(description = "每秒生成令牌数")
    private Integer replenishRate;

    @NotNull
    @Schema(description = "令牌桶容量")
    private Integer burstCapacity;

    @NotNull
    @Schema(description = "请求所需令牌数")
    private Integer requestedTokens;
}
