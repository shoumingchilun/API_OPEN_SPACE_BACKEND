package com.chilun.apiopenspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chilun.apiopenspace.mapper.RateLimitStrategyMapper;
import com.chilun.apiopenspace.model.entity.InterfaceInfo;
import com.chilun.apiopenspace.model.entity.RateLimitStrategy;
import com.chilun.apiopenspace.service.InterfaceInfoService;
import com.chilun.apiopenspace.service.RateLimitStrategyService;
import com.chilun.apiopenspace.service.RouteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 齿轮
 * @date 2024-03-10-18:38
 */
@Service("RateLimitStrategy&RouteService")
public class DecoratedRateLimitStrategyWithRouteService extends ServiceImpl<RateLimitStrategyMapper, RateLimitStrategy>
        implements RateLimitStrategyService {
    @Resource(name = "RateLimitStrategyService")
    RateLimitStrategyService baseService;

    @Resource(name = "InterfaceInfo&RouteService")
    RouteService routeService;

    @Resource(name = "InterfaceInfoService")
    InterfaceInfoService interfaceInfoService;

    @Override
    public RateLimitStrategy alterRateLimitStrategy(Long id, Integer replenishRate, Integer burstCapacity, Integer requestedTokens) {
        RateLimitStrategy rateLimitStrategy = baseService.alterRateLimitStrategy(id, replenishRate, burstCapacity, requestedTokens);
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        routeService.saveOrUpdateRoute(id, interfaceInfo.getRequestPath(), replenishRate, burstCapacity, requestedTokens);
        return rateLimitStrategy;
    }

    @Override
    public void deleteRateLimitStrategy(Long id) {
        baseService.deleteRateLimitStrategy(id);
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        routeService.saveOrUpdateRoute(id, interfaceInfo.getRequestPath());
    }
}
