package com.chilun.apiopenspace.controller;

import com.chilun.apiopenspace.Utils.ResultUtils;
import com.chilun.apiopenspace.model.dto.BaseResponse;
import com.chilun.apiopenspace.service.RouteService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 齿轮
 * @date 2024-02-14-14:59
 */
@RestController
@RequestMapping("/route")
@Api(tags = "路由管理控制器")
public class RouteController {
    @Resource(name = "InterfaceInfo&RouteService")
    RouteService service;

    @GetMapping("/refresh")
    @Operation(description = "刷新网关端路由")
    public BaseResponse<Void> refreshRoute() {
        service.refreshRoute();
        return ResultUtils.success(null);
    }

    @GetMapping("/init")
    @Operation(description = "初始化网关端路由")
    public BaseResponse<Void> initRoute() {
        service.initRoute();
        return ResultUtils.success(null);
    }
}
