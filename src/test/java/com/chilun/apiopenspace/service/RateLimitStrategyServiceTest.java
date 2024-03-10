package com.chilun.apiopenspace.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 齿轮
 * @date 2024-03-08-14:30
 */
@SpringBootTest
class RateLimitStrategyServiceTest {
    @Resource(name = "RateLimitStrategyService")
    RateLimitStrategyService service;

    @Test
    void testRateLimitStrategy() {
//        service.alterRateLimitStrategy(8L,1,2,3);
//        service.deleteRateLimitStrategy(9L);
    }
}