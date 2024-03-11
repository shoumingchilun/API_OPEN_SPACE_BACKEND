package com.chilun.apiopenspace.controller;

import com.chilun.apiopenspace.service.feign.FeignRouteService;
import com.chilun.apiopenspace.model.dto.DeleteRequest;
import com.chilun.apiopenspace.model.dto.Route.InitRouteRequest;
import com.chilun.apiopenspace.model.dto.Route.SaveOrUpdateRouteRequest;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author 齿轮
 * @date 2023-12-21-0:01
 */
@RestController
@RequestMapping("/test")
@Api(tags = "仅测试，请勿调用")
public class TestController {
    @Resource
    FeignRouteService routeService;

    @GetMapping("/base")
    @Operation(summary = "测试项目是否启动")
    public String testBase() {
        return "hello API_OPEN_SPACE！！！";
    }

    @GetMapping("/route")
    @Operation(summary = "测试路由服务")
    public String testRoute() {
        SaveOrUpdateRouteRequest saveOrUpdateRouteRequest = new SaveOrUpdateRouteRequest();
        saveOrUpdateRouteRequest.setId("1");
        saveOrUpdateRouteRequest.setUri("https://www.baidu.com");
        routeService.add(saveOrUpdateRouteRequest);

        InitRouteRequest initRouteRequest = new InitRouteRequest();
        SaveOrUpdateRouteRequest s1 = new SaveOrUpdateRouteRequest();
        s1.setId("2");
        s1.setUri("https://www.7k7k.com");
        SaveOrUpdateRouteRequest s2 = new SaveOrUpdateRouteRequest();
        s2.setId("3");
        s2.setUri("https://www.4399.com");
        initRouteRequest.setList(Arrays.asList(s1, s2));
        routeService.init(initRouteRequest);

        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setId(2L);
        routeService.delete(deleteRequest);

        return routeService.getAll().toString();
    }
//
//    @GetMapping("/user/register")
//    public String register(@RequestParam("account") String username,
//                           @RequestParam("password") String password,
//                           @RequestParam("checkedPassword") String checkedPassword) {
//        return String.valueOf(userService.userRegister(username, password, checkedPassword));
//    }
//
//    @GetMapping("/interface/get")
//    public String getInterFace() {
//        List<InterfaceInfo> interfaceInfos = interfaceInfoService.getBaseMapper().selectList(new QueryWrapper<>());
//        return interfaceInfos.toString();
//    }
}
