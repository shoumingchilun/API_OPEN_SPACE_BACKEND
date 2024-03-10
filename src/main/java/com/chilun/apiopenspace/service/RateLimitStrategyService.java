package com.chilun.apiopenspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chilun.apiopenspace.model.entity.RateLimitStrategy;


/**
* @author 齿轮
* @description 针对表【rate_limit_strategy(接口访问RPS限制表)】的数据库操作Service
* @createDate 2024-03-10 17:22:10
*/
public interface RateLimitStrategyService extends IService<RateLimitStrategy> {
    RateLimitStrategy alterRateLimitStrategy(Long id, Integer replenishRate, Integer burstCapacity, Integer requestedTokens);
    void deleteRateLimitStrategy(Long id);
}
