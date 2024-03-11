package com.chilun.apiopenspace.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chilun.apiopenspace.Utils.CryptographicUtils;
import com.chilun.apiopenspace.Utils.ResultUtils;
import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.aop.annotation.GatewayIPCheck;
import com.chilun.apiopenspace.aop.annotation.UserAuthCheck;
import com.chilun.apiopenspace.constant.UserRoleValue;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.model.Masked.InterfaceAccessMasked;
import com.chilun.apiopenspace.model.dto.BaseResponse;
import com.chilun.apiopenspace.model.dto.InterfaceAccess.*;
import com.chilun.apiopenspace.model.dto.PageRequest;
import com.chilun.apiopenspace.model.entity.InterfaceAccess;
import com.chilun.apiopenspace.model.entity.User;
import com.chilun.apiopenspace.service.InterfaceAccessService;
import com.chilun.apiopenspace.service.InterfaceInfoService;
import com.chilun.apiopenspace.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 申请接口、切换验证方式、撤销申请；用户自身查改；管理员增删改；管理员查/筛选；网关查看accesskey具体信息
 *
 * @author 齿轮
 * @date 2024-02-15-22:00
 */
@Slf4j
@RestController
@RequestMapping("/interfaceAccess")
@Api(tags = "接口访问管理控制器")
public class InterfaceAccessController {
    @Resource
    InterfaceAccessService interfaceAccessService;

    @Resource
    UserService userService;

    @Resource(name = "InterfaceInfoService")
    InterfaceInfoService interfaceInfoService;

    //****************服务层接口*****************
    //申请接口
    @PostMapping("/apply")
    @Operation(summary = "申请接口访问码的接口")
    public BaseResponse<InterfaceAccess> interfaceApply(@RequestBody @Valid @Parameter(description = "接口申请DTO") InterfaceApplyRequest applyRequest,
                                                        HttpServletRequest request) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解实现
        //2DTO参数是否异常（null/长度/数值）——@Valid注解实现

        //二、获得当前登录用户id
        User loggedInUser = userService.getLoggedInUser(request);
        Long userid = loggedInUser.getId();

        //三、进行注册
        InterfaceAccess interfaceAccess = interfaceAccessService.InterfaceApply(userid, applyRequest.getInterfaceId());

        //四、返回注册结果
        return ResultUtils.success(interfaceAccess);
    }

    //撤销申请
    @PostMapping("/abolish")
    @Operation(summary = "撤销接口访问码")
    public BaseResponse<Void> accesskeyAbolish(@RequestBody @Valid @Parameter(description = "撤销申请DTO") AccesskeyAbolishRequest abolishRequest
            , HttpServletRequest request) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解实现
        //2DTO参数是否异常（null/长度/数值）——@Valid注解实现
        //3请求对象是否为空
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR, "请求为空");

        //二、检查有无权限：要求——当前登录账户id为接口所有者的id
        //1获得当前用户
        User loggedInUser = userService.getLoggedInUser(request);
        //2获得待修改访问码信息
        InterfaceAccess pendingAccesskey = interfaceAccessService.getOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", abolishRequest.getAccesskey()));
        ThrowUtils.throwIf(pendingAccesskey == null, ErrorCode.PARAMS_ERROR, "申请不存在！");
        //3检查有无权限
        ThrowUtils.throwIf(!Objects.equals(pendingAccesskey.getUserid(), loggedInUser.getId()), ErrorCode.NO_AUTH_ERROR, "非所有者无权废除！");

        //三、进行废除
        interfaceAccessService.accesskeyAbolish(abolishRequest.getAccesskey());

        //四、返回结果
        return ResultUtils.success(null);
    }

    //切换验证方式（强校验/弱校验）
    @PostMapping("/changeVerify")
    @Operation(summary = "切换接口访问码对应的验证方式")
    public BaseResponse<InterfaceAccessMasked> changeVerifyType(@RequestBody @Valid @Parameter(description = "切换验证方式DTO") ChangeVerifyRequest changeRequest,
                                                                HttpServletRequest request) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解实现
        //2DTO参数是否异常（null/长度/数值）——@Valid注解实现
        //3请求对象是否为空
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR, "请求为空");

        //二、检查有无权限：要求——当前登录账户id为接口所有者的id
        //1获得当前用户
        User loggedInUser = userService.getLoggedInUser(request);
        //2获得待修改端口信息
        InterfaceAccess pendingAccesskey = interfaceAccessService.getOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", changeRequest.getAccesskey()));
        ThrowUtils.throwIf(pendingAccesskey == null, ErrorCode.PARAMS_ERROR, "申请不存在！");
        //3检查有无权限
        ThrowUtils.throwIf(!Objects.equals(pendingAccesskey.getUserid(), loggedInUser.getId()), ErrorCode.NO_AUTH_ERROR, "非所有者无权修改！");

        //三、进行修改
        InterfaceAccess interfaceAccess = interfaceAccessService.changeVerifyType(changeRequest.getAccesskey(), changeRequest.getType());

        //四、返回结果（脱敏并返回）
        return ResultUtils.success(interfaceAccessService.getInterfaceAccessMasked(interfaceAccess));
    }

    //**************************用户自身查改***************************
    //用户查自己的申请的接口
    @PostMapping("/querySelf")
    @Operation(summary = "用户查自己的申请过的接口的所有访问码")
    public BaseResponse<Page<InterfaceAccessMasked>> querySelf(@RequestBody(required = false) @Parameter(description = "分页参数") PageRequest pageRequest, HttpServletRequest request) {
        //一、数据校验
        //1DTO对象是否为空——可为空
        //2获得DTO对象分页参数
        if (pageRequest == null)
            pageRequest = new PageRequest();
        long current = pageRequest.getCurrent();
        long pageSize = pageRequest.getPageSize();
        //3请求对象是否为空
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR, "请求为空");

        //二、构建筛选条件
        //1获得当前用户
        User loggedInUser = userService.getLoggedInUser(request);
        //2构建筛选条件
        QueryWrapper<InterfaceAccess> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", loggedInUser.getId());

        //三、进行筛选
        Page<InterfaceAccess> page = interfaceAccessService.page(new Page<>(current, pageSize), wrapper);

        //四、返回脱敏后结果
        //1获得脱敏列表
        List<InterfaceAccessMasked> maskedList = interfaceAccessService.getInterfaceAccessMasked(page.getRecords());
        //2制作新的page
        Page<InterfaceAccessMasked> maskedPage = new Page<>();
        maskedPage.setRecords(maskedList);
        maskedPage.setCurrent(page.getCurrent());
        maskedPage.setSize(page.getSize());
        maskedPage.setTotal(page.getTotal());
        maskedPage.setPages(page.getPages());
        return ResultUtils.success(maskedPage);
    }

    //扣费模块正在完成......

    //充钱接口（简单实现）
    @PostMapping("/charge")
    @Operation(summary = "直接加余额（测试用）")
    public BaseResponse<InterfaceAccessMasked> topUp(@RequestBody @Valid @Parameter(description = "充钱DTO") TopUpRequest topUpRequest) {
        String accesskey = topUpRequest.getAccesskey();
        InterfaceAccess interfaceAccess = interfaceAccessService.getOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", accesskey));
        ThrowUtils.throwIf(interfaceAccess == null, ErrorCode.PARAMS_ERROR, "申请不存在！");
        interfaceAccess.setRemainingAmount(interfaceAccess.getRemainingAmount().add(topUpRequest.getAmount()));
        int update = interfaceAccessService.getBaseMapper().update(interfaceAccess, new QueryWrapper<InterfaceAccess>().eq("accesskey", accesskey));
        ThrowUtils.throwIf(update == 0, ErrorCode.PARAMS_ERROR, "更新失败！");
        InterfaceAccess updatedInterfaceAccess = interfaceAccessService.getOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", accesskey));
        return ResultUtils.success(interfaceAccessService.getInterfaceAccessMasked(updatedInterfaceAccess));
    }

    //**************************管理员接口***************************
    //管理员添加接口申请
    @PostMapping("/admin/add")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    @Operation(summary = "管理员添加接口访问码")
    public BaseResponse<InterfaceAccess> adminAddInterfaceAccess(@RequestBody @Valid @Parameter(description = "管理员添加接口访问码DTO") AccesskeyAddRequest addRequest) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解实现
        //2DTO参数是否异常（null/长度/数值）——@Valid注解实现

        //二、添加接口
        //1获得userid和接口id进行申请
        InterfaceAccess interfaceAccess = interfaceAccessService.InterfaceApply(addRequest.getUserid(), addRequest.getInterfaceId());
        //2生成新的InterfaceAccess，包含请求属性，用于更新
        InterfaceAccess interfaceAccess1 = new InterfaceAccess();
        BeanUtils.copyProperties(addRequest, interfaceAccess1);
        //3进行更新
        boolean update = interfaceAccessService.update(interfaceAccess1, new QueryWrapper<InterfaceAccess>().eq("accesskey", interfaceAccess.getAccesskey()));
        ThrowUtils.throwIf(!update, ErrorCode.SYSTEM_ERROR, "更新属性失败");

        //三、返回添加后的结果
        return ResultUtils.success(interfaceAccessService.getOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", interfaceAccess.getAccesskey())));
    }

    //管理员删除接口申请
    @PostMapping("/admin/delete")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    @Operation(summary = "管理员删除接口访问码")
    public BaseResponse<Void> adminDeleteAccessKey(@RequestBody @Valid @Parameter(description = "管理员删除接口访问码DTO") AccesskeyAbolishRequest deleteRequest) {
        //一、数据校验（已省略，通过Java Bean Validation实现）
        //1DTO对象是否为空——@RequestBody注解要求不为空
        //2DTO参数是否异常：必要参数是否存在——@Valid注解要求不为空

        //二、删除接口
        boolean b = interfaceAccessService.remove(new QueryWrapper<InterfaceAccess>().eq("accesskey", deleteRequest.getAccesskey()));
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR, "删除失败");

        //三、返回结果
        return ResultUtils.success(null);
    }

    //管理员更新接口申请
    @PostMapping("/admin/update")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    @Operation(summary = "管理员更新接口访问码")
    public BaseResponse<InterfaceAccess> adminUpdateInterfaceAccess(@RequestBody @Valid @Parameter(description = "管理员更新接口访问码DTO") AccesskeyUpdateRequest updateRequest) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解实现
        //2DTO参数是否异常（null/长度/数值）——@Valid注解实现
        //3DTO对象参数不全为空校验
        ThrowUtils.throwIf(
                ObjectUtils.allNull(updateRequest.getInterfaceId(),
                        updateRequest.getUserid(),
                        updateRequest.getVerifyType(),
                        updateRequest.getRemainingAmount(),
                        updateRequest.getCost())
                , ErrorCode.PARAMS_ERROR, "无可更改参数");
        //4校验用户id/接口id/Accesskey参数是否合理（存在）
        InterfaceAccess newInterfaceInfo = interfaceAccessService.getOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", updateRequest.getAccesskey()));
        ThrowUtils.throwIf(newInterfaceInfo == null, ErrorCode.PARAMS_ERROR, "接口申请不存在！");
        ThrowUtils.throwIf(updateRequest.getUserid() != null && userService.getById(updateRequest.getUserid()) == null,
                ErrorCode.PARAMS_ERROR, "所选择用户不存在！");
        ThrowUtils.throwIf(updateRequest.getInterfaceId() != null && interfaceInfoService.getById(updateRequest.getInterfaceId()) == null,
                ErrorCode.PARAMS_ERROR, "所选择接口不存在！");

        //二、进行修改
        //1对需要修改的属性进行填充
        BeanUtils.copyProperties(updateRequest, newInterfaceInfo);
        //2进行更新
        boolean update = interfaceAccessService.update(newInterfaceInfo, new QueryWrapper<InterfaceAccess>().eq("accesskey", updateRequest.getAccesskey()));
        ThrowUtils.throwIf(!update, ErrorCode.SYSTEM_ERROR, "更新失败");

        //三、返回修改后结果
        return ResultUtils.success(interfaceAccessService.getOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", updateRequest.getAccesskey())));
    }

    //管理员查询\筛选接口：ByAccesskey
    @GetMapping("/admin/query/{accesskey}")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    @Operation(summary = "管理员查询接口访问码：ByAccesskey")
    public BaseResponse<InterfaceAccess> getInterfaceAccessByID(@PathVariable @Parameter(description = "接口访问码") String accesskey) {
        //一、数据校验
        //1id是否为空——@PathVariable注解要求不为空

        //二、查询结果并返回
        return ResultUtils.success(interfaceAccessService.getOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", accesskey)));
    }

    //管理员查询\筛选接口：（all）、byUserid、byInterfaceId、orderByRemainingAmount/times/type
    // isCost、isRestrict、isExceptionHandling
    @PostMapping("/admin/filter")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    @Operation(summary = "管理员筛选接口访问码")
    public BaseResponse<Page<InterfaceAccess>> listInterfaceAccess(@RequestBody @Valid @Parameter(description = "管理员筛选接口访问码DTO") AccesskeyQueryRequest queryRequest) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解要求不为空
        //2获得DTO对象分页参数
        long current = queryRequest.getCurrent();
        long pageSize = queryRequest.getPageSize();

        //二、构建筛选条件
        QueryWrapper<InterfaceAccess> wrapper = new QueryWrapper<>();
        wrapper.eq(queryRequest.getUserid() != null, "userid", queryRequest.getUserid());
        wrapper.eq(queryRequest.getInterfaceId() != null, "interface_id", queryRequest.getInterfaceId());
        wrapper.orderBy(queryRequest.getSortField() != null,
                "ascend".equals(queryRequest.getSortOrder()), queryRequest.getSortField());
        //三、进行筛选
        Page<InterfaceAccess> page = interfaceAccessService.page(new Page<>(current, pageSize), wrapper);

        //四、返回结果
        return ResultUtils.success(page);
    }

    //**************************网关远程调用接口****************************
    @Resource
    CryptographicUtils cryptographicUtils;

    @GetMapping("/gateway/query/{accesskey}")
    @GatewayIPCheck
    @Operation(summary = "网关远程调用，获得接口访问码相关信息（加密）")
    public BaseResponse<String> getCryptographicInterfaceAccess(@PathVariable("accesskey") @Parameter(description = "接口访问码") String accesskey) throws Exception {
        //一、数据校验
        //1DTO对象是否为空——@PathVariable注解要求不为空

        //二、获得对象
        InterfaceAccess interfaceAccess = interfaceAccessService.getOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", accesskey));
        ThrowUtils.throwIf(interfaceAccess == null, ErrorCode.PARAMS_ERROR, "接口申请不存在");
        ObjectMapper mapper = new ObjectMapper();
        String jsonAccess = mapper.writeValueAsString(interfaceAccess);
        String encryptedText = cryptographicUtils.encryptAES(jsonAccess);
        return ResultUtils.success(encryptedText);
    }
}