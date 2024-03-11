package com.chilun.apiopenspace.controller;

import com.chilun.apiopenspace.Utils.ResultUtils;
import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.aop.annotation.UserAuthCheck;
import com.chilun.apiopenspace.constant.UserRoleValue;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.model.dto.BaseResponse;
import com.chilun.apiopenspace.model.dto.DeleteRequest;
import com.chilun.apiopenspace.model.dto.RateLimitStrategy.RateLimitSetRequest;
import com.chilun.apiopenspace.model.entity.InterfaceChargeStrategy;
import com.chilun.apiopenspace.model.entity.InterfaceInfo;
import com.chilun.apiopenspace.model.entity.RateLimitStrategy;
import com.chilun.apiopenspace.model.entity.User;
import com.chilun.apiopenspace.service.InterfaceInfoService;
import com.chilun.apiopenspace.service.RateLimitStrategyService;
import com.chilun.apiopenspace.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author 齿轮
 * @description 用于修改接口限流策略的控制权。注意：限流对象为accesskey。
 * @date 2024-03-10-17:46
 */
@RestController
@RequestMapping("/rate")
@Api(tags = "限流策略管理控制器")
public class RateLimitController {
    @Resource(name = "RateLimitStrategy&RouteService")
    RateLimitStrategyService rateLimitStrategyService;

    @Resource
    UserService userService;

    @Resource(name = "InterfaceInfoService")
    InterfaceInfoService interfaceInfoService;


    @PostMapping("/set")
    @Operation(summary = "修改接口限流策略")
    public BaseResponse<RateLimitStrategy> setRateLimitStrategy(
            @RequestBody @Valid @Parameter(description = "接口限流策略修改DTO") RateLimitSetRequest setRequest, HttpServletRequest request) {
        //一、参数校验
        //1空值校验：@RequestBody不可为空；@Valid：属性不可为空
        //2权限校验
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(setRequest.getId());
        User loggedInUser = userService.getLoggedInUser(request);
        ThrowUtils.throwIf(!Objects.equals(interfaceInfo.getUserid(), loggedInUser.getId()), ErrorCode.NO_AUTH_ERROR, "非接口所有者，无权修改收费策略");

        //二、进入修改
        RateLimitStrategy rateLimitStrategy = rateLimitStrategyService.alterRateLimitStrategy(setRequest.getId(), setRequest.getReplenishRate(), setRequest.getBurstCapacity(), setRequest.getRequestedTokens());

        //三、返回结果
        return ResultUtils.success(rateLimitStrategy);
    }

    @PostMapping("/remove")
    @Operation(summary = "删除接口限流策略")
    public BaseResponse<Void> removeRateLimitStrategy(
            @RequestBody @Valid @Parameter(description = "接口限流策略删除DTO") DeleteRequest deleteRequest, HttpServletRequest request) {
        //一、参数校验
        //1空值校验：@RequestBody不可为空；@Valid：属性不可为空
        //2权限校验
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(deleteRequest.getId());
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.PARAMS_ERROR, "接口不存在");
        User loggedInUser = userService.getLoggedInUser(request);
        ThrowUtils.throwIf(!Objects.equals(interfaceInfo.getUserid(), loggedInUser.getId()), ErrorCode.NO_AUTH_ERROR, "非接口所有者，无权修改收费策略");

        //二、开始删除
        rateLimitStrategyService.deleteRateLimitStrategy(deleteRequest.getId());

        //三、返回结果
        return ResultUtils.success(null);
    }

    @GetMapping("/query/{id}")
    @Operation(summary = "查询接口限流策略")
    public BaseResponse<RateLimitStrategy> getRateLimitStrategy(@PathVariable @Parameter(description = "限流策略id") Long id) {
        //一、参数校验
        //1空值校验：@PathVariable不可为空
        //二、开始查找
        RateLimitStrategy limitStrategy = rateLimitStrategyService.getById(id);

        //三、返回结果
        return ResultUtils.success(limitStrategy);
    }

    @PostMapping("/admin/set")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    @Operation(summary = "管理员修改接口限流策略")
    public BaseResponse<RateLimitStrategy> adminSetOrChangeRateLimitStrategy(
            @RequestBody @Valid @Parameter(description = "接口限流策略修改DTO") RateLimitSetRequest setRequest) {
        //一、参数校验
        //1空值校验：@RequestBody不可为空；@Valid：参数不可为空
        //2权限校验：AOP实现

        //二、进入修改
        RateLimitStrategy rateLimitStrategy = rateLimitStrategyService.alterRateLimitStrategy(setRequest.getId(), setRequest.getReplenishRate(), setRequest.getBurstCapacity(), setRequest.getRequestedTokens());

        //三、返回结果
        return ResultUtils.success(rateLimitStrategy);
    }

    @PostMapping("/admin/delete")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    @Operation(summary = "管理员删除接口限流策略")
    public BaseResponse<InterfaceChargeStrategy> adminDeleteChargeStrategy(
            @RequestBody @Valid @Parameter(description = "接口限流策略删除DTO") DeleteRequest deleteRequest) {
        //一、参数校验
        //1空值校验：@RequestBody不可为空；@Valid：id、type不可为空
        //2参数范围校验：@Valid 实现
        //3权限校验：AOP实现
        //二、开始删除
        rateLimitStrategyService.deleteRateLimitStrategy(deleteRequest.getId());

        //三、返回结果
        return ResultUtils.success(null);
    }
}
