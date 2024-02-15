package com.chilun.apiopenspace.controller;

import com.chilun.apiopenspace.Utils.ResultUtils;
import com.chilun.apiopenspace.model.dto.BaseResponse;
import com.chilun.apiopenspace.service.RouteService;
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
public class RouteController {
    @Resource(name = "InterfaceInfo&RouteService")
    RouteService service;

    @GetMapping("/refresh")
    public BaseResponse<Void> refreshRoute() {
        service.refreshRoute();
        return ResultUtils.success(null);
    }

    @GetMapping("/init")
    public BaseResponse<Void> initRoute() {
        service.initRoute();
        return ResultUtils.success(null);
    }
}
