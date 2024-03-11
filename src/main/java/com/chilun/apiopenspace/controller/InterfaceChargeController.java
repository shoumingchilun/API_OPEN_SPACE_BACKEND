package com.chilun.apiopenspace.controller;

import com.chilun.apiopenspace.Utils.ResultUtils;
import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.aop.annotation.UserAuthCheck;
import com.chilun.apiopenspace.constant.CostTypeValue;
import com.chilun.apiopenspace.constant.UserRoleValue;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.model.dto.BaseResponse;
import com.chilun.apiopenspace.model.dto.DeleteRequest;
import com.chilun.apiopenspace.model.dto.InterfaceChargeStrategy.InterfaceChargeSetRequest;
import com.chilun.apiopenspace.model.entity.InterfaceChargeStrategy;
import com.chilun.apiopenspace.model.entity.InterfaceInfo;
import com.chilun.apiopenspace.model.entity.User;
import com.chilun.apiopenspace.service.InterfaceChargeStrategyService;
import com.chilun.apiopenspace.service.InterfaceInfoService;
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
 * @date 2024-03-08-15:15
 */
@RestController
@RequestMapping("/interfaceCharge")
@Api(tags = "接口计费策略管理控制器")
public class InterfaceChargeController {
    @Resource
    InterfaceChargeStrategyService chargeStrategyService;

    @Resource
    UserService userService;

    @Resource(name = "InterfaceInfoService")
    InterfaceInfoService interfaceInfoService;

    @PostMapping("/set")
    @Operation(summary = "设置接口计费策略")
    public BaseResponse<InterfaceChargeStrategy> setInterfaceChargeStrategy(
            @RequestBody @Valid @Parameter(description = "接口计费策略设置DTO") InterfaceChargeSetRequest setRequest, HttpServletRequest request) {
        //一、参数校验
        //1空值校验：@RequestBody不可为空；@Valid：id、type不可为空
        ThrowUtils.throwIf(setRequest.getCostType() == CostTypeValue.FIXED_FEE && setRequest.getFixedFee() == null,
                ErrorCode.PARAMS_ERROR, "固定费用策略下，固定费用不能为空！");
        ThrowUtils.throwIf(setRequest.getCostType() == CostTypeValue.FIXED_TIME && setRequest.getFixedTime() == null,
                ErrorCode.PARAMS_ERROR, "固定时长策略下，固定时长不能为空！");
        //2参数范围校验：@Valid 实现
        //3权限校验
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(setRequest.getId());
        User loggedInUser = userService.getLoggedInUser(request);
        ThrowUtils.throwIf(!Objects.equals(interfaceInfo.getUserid(), loggedInUser.getId()), ErrorCode.NO_AUTH_ERROR, "非接口所有者，无权修改收费策略");

        //二、进入修改
        InterfaceChargeStrategy strategy = chargeStrategyService.alterInterfaceChargeStrategy(setRequest.getId(), setRequest.getCostType(), setRequest.getFixedFee(), setRequest.getFixedTime());

        //三、返回结果
        return ResultUtils.success(strategy);
    }

    @PostMapping("/remove")
    @Operation(summary = "删除接口计费策略")
    public BaseResponse<Void> removeInterfaceChargeStrategy(
            @RequestBody @Valid @Parameter(description = "接口计费策略删除DTO") DeleteRequest deleteRequest, HttpServletRequest request) {
        //一、参数校验
        //1空值校验：@RequestBody不可为空；@Valid：id、type不可为空
        //2参数范围校验：@Valid 实现
        //3权限校验
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(deleteRequest.getId());
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.PARAMS_ERROR, "接口不存在");
        User loggedInUser = userService.getLoggedInUser(request);
        ThrowUtils.throwIf(!Objects.equals(interfaceInfo.getUserid(), loggedInUser.getId()), ErrorCode.NO_AUTH_ERROR, "非接口所有者，无权修改收费策略");

        //二、开始删除
        chargeStrategyService.deleteInterfaceChargeStrategy(deleteRequest.getId());

        //三、返回结果
        return ResultUtils.success(null);
    }

    @GetMapping("/query/{id}")
    @Operation(summary = "查询接口计费策略")
    public BaseResponse<InterfaceChargeStrategy> getInterfaceChargeStrategy(@PathVariable @Parameter(description = "接口ID") Long id) {
        //一、参数校验
        //1空值校验：@PathVariable不可为空
        //二、开始查找
        InterfaceChargeStrategy interfaceChargeStrategy = chargeStrategyService.getById(id);

        //三、返回结果
        return ResultUtils.success(interfaceChargeStrategy);
    }

    @PostMapping("/admin/set")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    @Operation(summary = "管理员修改接口计费策略")
    public BaseResponse<InterfaceChargeStrategy> adminSetOrChangeChargeStrategy(
            @RequestBody @Valid @Parameter(description = "接口计费策略修改DTO") InterfaceChargeSetRequest setRequest) {
        //一、参数校验
        //1空值校验：@RequestBody不可为空；@Valid：id、type不可为空
        ThrowUtils.throwIf(setRequest.getCostType() == CostTypeValue.FIXED_FEE && setRequest.getFixedFee() == null,
                ErrorCode.PARAMS_ERROR, "固定费用策略下，固定费用不能为空！");
        ThrowUtils.throwIf(setRequest.getCostType() == CostTypeValue.FIXED_TIME && setRequest.getFixedTime() == null,
                ErrorCode.PARAMS_ERROR, "固定时长策略下，固定时长不能为空！");
        //2参数范围校验：@Valid 实现
        //3权限校验：AOP实现

        //二、进入修改
        InterfaceChargeStrategy strategy = chargeStrategyService.alterInterfaceChargeStrategy(setRequest.getId(), setRequest.getCostType(), setRequest.getFixedFee(), setRequest.getFixedTime());

        //三、返回结果
        return ResultUtils.success(strategy);
    }

    @PostMapping("/admin/delete")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    @Operation(summary = "管理员删除接口计费策略")
    public BaseResponse<InterfaceChargeStrategy> adminDeleteChargeStrategy(
            @RequestBody @Valid @Parameter(description = "接口计费策略删除DTO") DeleteRequest deleteRequest) {
        //一、参数校验
        //1空值校验：@RequestBody不可为空；@Valid：id、type不可为空
        //2参数范围校验：@Valid 实现
        //3权限校验：AOP实现
        //二、开始删除
        chargeStrategyService.deleteInterfaceChargeStrategy(deleteRequest.getId());

        //三、返回结果
        return ResultUtils.success(null);
    }
}
