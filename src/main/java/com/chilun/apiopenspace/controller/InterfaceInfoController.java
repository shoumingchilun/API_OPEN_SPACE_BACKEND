package com.chilun.apiopenspace.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chilun.apiopenspace.Utils.ResultUtils;
import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.aop.annotation.UserAuthCheck;
import com.chilun.apiopenspace.constant.SessionKey;
import com.chilun.apiopenspace.constant.UserRoleValue;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.model.Masked.InterfaceInfoMasked;
import com.chilun.apiopenspace.model.Masked.UserMasked;
import com.chilun.apiopenspace.model.dto.BaseResponse;
import com.chilun.apiopenspace.model.dto.DeleteRequest;
import com.chilun.apiopenspace.model.dto.InterfaceInfo.*;
import com.chilun.apiopenspace.model.dto.PageRequest;
import com.chilun.apiopenspace.model.entity.InterfaceInfo;
import com.chilun.apiopenspace.model.entity.User;
import com.chilun.apiopenspace.service.InterfaceInfoService;
import com.chilun.apiopenspace.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * @author 齿轮
 * @date 2024-01-27-23:07
 * <p>
 * 注册接口、状态管理（启用、禁用）、废弃接口；用户自身查改；管理员增删改；管理员查/筛选
 */
@RestController
@RequestMapping("/interfaceInfo")
public class InterfaceInfoController {
    @Resource(name = "InterfaceInfo&RouteService")
    InterfaceInfoService interfaceInfoService;

    @Resource
    UserService userService;


    //注册接口
    @PostMapping("/register")
    public BaseResponse<Long> interfaceRegister(@RequestBody @Valid InterfaceRegisterRequest registerRequest,HttpServletRequest request) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解实现
        //2DTO参数是否异常（null/长度/数值）——@Valid注解实现

        //二、进行注册
        User loggedInUser = userService.getLoggedInUser(request);
        long l = interfaceInfoService.InterfaceRegister(loggedInUser.getId(), registerRequest.getRequestPath(), registerRequest.getRequestMethod());

        //三、返回注册结果
        return ResultUtils.success(l);
    }

    //状态管理（启用、禁用）
    @PostMapping("/statusManage")
    public BaseResponse<InterfaceInfoMasked> statusManage(@RequestBody @Valid InterfaceStatusManageRequest statusManageRequest,
                                                          HttpServletRequest request) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解实现
        //2DTO参数是否异常（null/长度/数值）——@Valid注解实现
        //3请求对象是否为空
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR, "请求为空");

        //二、检查有无权限：要求——当前登录账户id为接口所有者的id
        //1获得当前用户
        UserMasked userMasked = (UserMasked) request.getSession().getAttribute(SessionKey.USER_IN_SESSION_KEY);
        ThrowUtils.throwIf(userMasked == null, ErrorCode.NOT_LOGIN_ERROR, "未登录！");
        //2获得待修改端口信息
        InterfaceInfo pendingInterface = interfaceInfoService.getById(statusManageRequest.getId());
        ThrowUtils.throwIf(pendingInterface == null, ErrorCode.PARAMS_ERROR, "接口不存在！");
        //3检查有无权限
        ThrowUtils.throwIf(!Objects.equals(pendingInterface.getUserid(), userMasked.getId()), ErrorCode.NO_AUTH_ERROR, "非所有者无权修改！");

        //三、进行修改
        InterfaceInfo interfaceInfo = interfaceInfoService.statusManage(statusManageRequest.getId(), statusManageRequest.getStatus());

        //四、返回结果（脱敏并返回）
        return ResultUtils.success(interfaceInfoService.getInterfaceInfoMasked(interfaceInfo));
    }

    //废弃接口
    @PostMapping("/abolish")
    public BaseResponse<Void> interfaceAbolish(@RequestBody @Valid DeleteRequest deleteRequest, HttpServletRequest request) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解实现
        //2DTO参数是否异常（null/长度/数值）——@Valid注解实现
        //3请求对象是否为空
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR, "请求为空");

        //二、检查有无权限：要求——当前登录账户id为接口所有者的id
        //1获得当前用户
        UserMasked userMasked = (UserMasked) request.getSession().getAttribute(SessionKey.USER_IN_SESSION_KEY);
        ThrowUtils.throwIf(userMasked == null, ErrorCode.NOT_LOGIN_ERROR, "未登录！");
        //2获得待修改端口信息
        InterfaceInfo pendingInterface = interfaceInfoService.getById(deleteRequest.getId());
        ThrowUtils.throwIf(pendingInterface == null, ErrorCode.PARAMS_ERROR, "接口不存在！");
        //3检查有无权限
        ThrowUtils.throwIf(!Objects.equals(pendingInterface.getUserid(), userMasked.getId()), ErrorCode.NO_AUTH_ERROR, "非所有者无权废除！");

        //三、进行废除
        interfaceInfoService.InterfaceAbolish(deleteRequest.getId());

        //四、返回结果
        return ResultUtils.success(null);
    }


    //用户更改接口信息
    @PostMapping("/update")
    public BaseResponse<InterfaceInfoMasked> update(@RequestBody @Valid InterfaceUpdateRequest updateRequest,
                                                    HttpServletRequest request) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解实现
        //2DTO参数是否异常（null/长度/数值）——@Valid注解实现
        //3DTO对象参数不全为空校验
        ThrowUtils.throwIf(ObjectUtils.allNull(updateRequest.getRequestPath(),
                updateRequest.getRequestExample(),
                updateRequest.getIntroduction(),
                updateRequest.getRequestMethod(),
                updateRequest.getRequestParameters(),
                updateRequest.getRequestDataType(),
                updateRequest.getResponseExample(),
                updateRequest.getResponseParameters(),
                updateRequest.getResponseStatus(),
                updateRequest.getIsCost(),
                updateRequest.getIsRestrict(),
                updateRequest.getIsExceptionHandling(),
                updateRequest.getStatus()), ErrorCode.PARAMS_ERROR, "无可更改参数");
        //4请求对象是否为空
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR, "请求为空");

        //二、检查有无权限：要求——当前登录账户id为接口所有者的id
        //1获得当前用户
        UserMasked userMasked = (UserMasked) request.getSession().getAttribute(SessionKey.USER_IN_SESSION_KEY);
        ThrowUtils.throwIf(userMasked == null, ErrorCode.NOT_LOGIN_ERROR, "未登录！");
        //2获得待修改端口信息
        InterfaceInfo pendingInterface = interfaceInfoService.getById(updateRequest.getId());
        ThrowUtils.throwIf(pendingInterface == null, ErrorCode.PARAMS_ERROR, "接口不存在！");
        //3检查有无权限
        ThrowUtils.throwIf(!Objects.equals(pendingInterface.getUserid(), userMasked.getId()), ErrorCode.NO_AUTH_ERROR, "非所有者无权修改！");

        //三、进行修改
        //1获得新接口对象
        BeanUtils.copyProperties(updateRequest, pendingInterface);
        //2进行更新
        boolean update = interfaceInfoService.updateById(pendingInterface);
        ThrowUtils.throwIf(!update, ErrorCode.SYSTEM_ERROR, "更新失败");

        //四、返回修改后脱敏结果
        InterfaceInfo byId = interfaceInfoService.getById(pendingInterface.getId());
        return ResultUtils.success(interfaceInfoService.getInterfaceInfoMasked(byId));
    }

    //用户查自己接口
    @PostMapping("/querySelf")
    public BaseResponse<Page<InterfaceInfoMasked>> querySelf(@RequestBody(required = false) PageRequest pageRequest, HttpServletRequest request) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解要求不为空
        //2获得DTO对象分页参数
        if (pageRequest == null)
            pageRequest = new PageRequest();
        long current = pageRequest.getCurrent();
        long pageSize = pageRequest.getPageSize();
        //3请求对象是否为空
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR, "请求为空");

        //二、构建筛选条件
        //1获得当前用户
        UserMasked userMasked = (UserMasked) request.getSession().getAttribute(SessionKey.USER_IN_SESSION_KEY);
        ThrowUtils.throwIf(userMasked == null, ErrorCode.NOT_LOGIN_ERROR, "未登录！");
        //2构建筛选条件
        QueryWrapper<InterfaceInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userMasked.getId());

        //三、进行筛选
        Page<InterfaceInfo> page = interfaceInfoService.page(new Page<>(current, pageSize), wrapper);

        //四、返回脱敏并结果
        //1获得脱敏列表
        List<InterfaceInfoMasked> maskedList = interfaceInfoService.getInterfaceInfoMasked(page.getRecords());
        //2制作新的page
        Page<InterfaceInfoMasked> maskedPage = new Page<>();
        maskedPage.setRecords(maskedList);
        maskedPage.setCurrent(page.getCurrent());
        maskedPage.setSize(page.getSize());
        maskedPage.setTotal(page.getTotal());
        maskedPage.setPages(page.getPages());
        return ResultUtils.success(maskedPage);
    }

    //用户通过introduction查询别的接口
    @PostMapping("/queryOthers")
    public BaseResponse<Page<InterfaceInfoMasked>> queryOthers(@RequestBody InterfaceQueryOthersRequest queryOthersRequest) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解实现
        //2DTO参数是否异常（null/长度/数值）——@Valid注解实现
        long current = queryOthersRequest.getCurrent();
        long pageSize = queryOthersRequest.getPageSize();

        //二、构建筛选条件
        //1构建筛选条件
        QueryWrapper<InterfaceInfo> wrapper = new QueryWrapper<>();
        wrapper.like("introduction", queryOthersRequest.getLikeIntroduction());

        //三、进行筛选
        Page<InterfaceInfo> page = interfaceInfoService.page(new Page<>(current, pageSize), wrapper);

        //四、返回脱敏并结果
        //1获得脱敏列表
        List<InterfaceInfoMasked> maskedList = interfaceInfoService.getInterfaceInfoMasked(page.getRecords());
        //2制作新的page
        Page<InterfaceInfoMasked> maskedPage = new Page<>();
        maskedPage.setRecords(maskedList);
        maskedPage.setCurrent(page.getCurrent());
        maskedPage.setSize(page.getSize());
        maskedPage.setTotal(page.getTotal());
        maskedPage.setPages(page.getPages());
        return ResultUtils.success(maskedPage);
    }

    //管理员添加接口
    @PostMapping("/admin/add")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    public BaseResponse<InterfaceInfo> adminAddInterface(@RequestBody @Valid InterfaceAddRequest addRequest) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解实现
        //2DTO参数是否异常（null/长度/数值）——@Valid注解实现

        //二、添加接口
        //1生成添加的接口类
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(addRequest, interfaceInfo);
        //2通过service添加用户
        boolean saved = interfaceInfoService.save(interfaceInfo);
        ThrowUtils.throwIf(!saved, ErrorCode.SYSTEM_ERROR, "添加失败");

        //三、返回添加后的结果
        return ResultUtils.success(interfaceInfoService.getById(interfaceInfo.getId()));
    }

    //管理员删除接口
    @PostMapping("/admin/delete")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    public BaseResponse<Void> adminDeleteInterface(@RequestBody @Valid DeleteRequest deleteRequest) {
        //一、数据校验（已省略，通过Java Bean Validation实现）
        //1DTO对象是否为空——@RequestBody注解要求不为空
        //2DTO参数是否异常：必要参数是否存在——@Valid注解要求不为空

        //二、删除接口
        boolean b = interfaceInfoService.removeById(deleteRequest.getId());
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR, "删除失败");

        //三、返回结果
        return ResultUtils.success(null);
    }

    //管理员更新接口
    @PostMapping("/admin/update")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    public BaseResponse<InterfaceInfo> adminUpdateInterface(@RequestBody @Valid InterfaceUpdateRequest updateRequest) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解实现
        //2DTO参数是否异常（null/长度/数值）——@Valid注解实现
        //3DTO对象参数不全为空校验
        ThrowUtils.throwIf(ObjectUtils.allNull(updateRequest.getRequestPath(),
                updateRequest.getRequestExample(),
                updateRequest.getIntroduction(),
                updateRequest.getRequestMethod(),
                updateRequest.getRequestParameters(),
                updateRequest.getRequestDataType(),
                updateRequest.getResponseExample(),
                updateRequest.getResponseParameters(),
                updateRequest.getResponseStatus(),
                updateRequest.getIsCost(),
                updateRequest.getIsRestrict(),
                updateRequest.getIsExceptionHandling(),
                updateRequest.getStatus()), ErrorCode.PARAMS_ERROR, "无可更改参数");

        //二、获得待修改端口信息
        InterfaceInfo pendingInterface = interfaceInfoService.getById(updateRequest.getId());
        ThrowUtils.throwIf(pendingInterface == null, ErrorCode.PARAMS_ERROR, "接口不存在！");

        //三、进行修改
        //1获得新接口对象
        InterfaceInfo newInterfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(updateRequest, newInterfaceInfo);
        //2进行更新
        boolean update = interfaceInfoService.updateById(newInterfaceInfo);
        ThrowUtils.throwIf(!update, ErrorCode.SYSTEM_ERROR, "更新失败");

        //四、返回修改后结果
        return ResultUtils.success(interfaceInfoService.getById(newInterfaceInfo.getId()));
    }

    //管理员查询\筛选接口：ByID
    @GetMapping("/admin/query/{id}")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    public BaseResponse<InterfaceInfo> getInterfaceInfoByID(@PathVariable Long id) {
        //一、数据校验
        //1id是否为空——@PathVariable注解要求不为空

        //二、查询结果并返回
        return ResultUtils.success(interfaceInfoService.getById(id));
    }

    //管理员查询\筛选接口：（all）、byUserid、likeRequestPath、likeIntroduction、inRequestMethod、inStatus
    // isCost、isRestrict、isExceptionHandling
    @PostMapping("/admin/filter")
    @UserAuthCheck(mustRole = UserRoleValue.ADMIN)
    public BaseResponse<Page<InterfaceInfo>> listInterface(@RequestBody @Valid InterfaceQueryRequest queryRequest) {
        //一、数据校验
        //1DTO对象是否为空——@RequestBody注解要求不为空
        //2获得DTO对象分页参数
        long current = queryRequest.getCurrent();
        long pageSize = queryRequest.getPageSize();

        //二、构建筛选条件
        QueryWrapper<InterfaceInfo> wrapper = new QueryWrapper<>();
        wrapper.eq(queryRequest.getUserid() != null, "userid", queryRequest.getUserid());
        wrapper.like(queryRequest.getLikeRequestPath() != null, "request_path", queryRequest.getLikeRequestPath());
        wrapper.like(queryRequest.getLikeIntroduction() != null, "introduction", queryRequest.getLikeIntroduction());
        if (queryRequest.getInRequestMethod() != null && queryRequest.getInRequestMethod().length != 0)
            wrapper.in("request_method", List.of(queryRequest.getInRequestMethod()));
        if (queryRequest.getInStatus() != null && queryRequest.getInStatus().length != 0)
            wrapper.in("status", List.of(queryRequest.getInStatus()));
        wrapper.eq(queryRequest.getIsCost() != null, "is_cost", queryRequest.getIsCost());
        wrapper.eq(queryRequest.getIsRestrict() != null, "is_restrict", queryRequest.getIsRestrict());
        wrapper.eq(queryRequest.getIsExceptionHandling() != null, "is_exception_handling", queryRequest.getIsExceptionHandling());

        //三、进行筛选
        Page<InterfaceInfo> page = interfaceInfoService.page(new Page<>(current, pageSize), wrapper);

        //四、返回结果
        return ResultUtils.success(page);
    }
}