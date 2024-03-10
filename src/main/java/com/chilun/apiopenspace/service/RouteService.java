package com.chilun.apiopenspace.service;


/**
 * @author 齿轮
 * @description 用于管理路由的接口
 * @date 2024-02-14-15:01
 */
public interface RouteService {
     void refreshRoute();

    void saveOrUpdateRoute(Long id, String uri);

    void saveOrUpdateRoute(Long id, String uri,Integer replenishRate,Integer burstCapacity,Integer requestedTokens);

    void deleteRoute(Long id);

     void initRoute();
}
