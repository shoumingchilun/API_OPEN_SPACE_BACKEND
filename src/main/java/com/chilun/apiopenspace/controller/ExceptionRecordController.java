package com.chilun.apiopenspace.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chilun.apiopenspace.Utils.ResultUtils;
import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.model.Masked.ExceptionRecordMasked;
import com.chilun.apiopenspace.model.dto.BaseResponse;
import com.chilun.apiopenspace.model.dto.PageRequest;
import com.chilun.apiopenspace.model.entity.ExceptionRecord;
import com.chilun.apiopenspace.model.entity.InterfaceAccess;
import com.chilun.apiopenspace.model.entity.InterfaceInfo;
import com.chilun.apiopenspace.model.entity.User;
import com.chilun.apiopenspace.service.ExceptionRecordService;
import com.chilun.apiopenspace.service.InterfaceAccessService;
import com.chilun.apiopenspace.service.InterfaceInfoService;
import com.chilun.apiopenspace.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 提供查询方法，支持通过accesskey、userid（用户自身查询）、interfaceId进行查询
 *
 * @author 齿轮
 * @date 2024-02-27-16:11
 */
@RestController
@RequestMapping("/exceptionRecord")
@Api(tags = "异常记录管理控制器")
public class ExceptionRecordController {
    @Resource
    private UserService userService;

    @Resource
    private ExceptionRecordService exceptionRecordService;

    @Resource(name = "InterfaceInfoService")
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private InterfaceAccessService interfaceAccessService;

    @PostMapping("/querySelf")
    @Operation(summary = "用户查询自身的异常访问记录")
    public BaseResponse<Page<ExceptionRecordMasked>> querySelf(@RequestBody(required = false) @Parameter(description = "分页参数") PageRequest pageRequest, HttpServletRequest request) {
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
        //2构建条件
        QueryWrapper<ExceptionRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", loggedInUser.getId());

        //三、进行筛选
        Page<ExceptionRecord> page = exceptionRecordService.page(new Page<>(current, pageSize), wrapper);

        //四、返回脱敏并结果
        //1获得脱敏列表
        List<ExceptionRecordMasked> maskedList = exceptionRecordService.getExceptionRecordMasked(page.getRecords());
        //2制作新的page
        Page<ExceptionRecordMasked> maskedPage = new Page<>();
        maskedPage.setRecords(maskedList);
        maskedPage.setCurrent(page.getCurrent());
        maskedPage.setSize(page.getSize());
        maskedPage.setTotal(page.getTotal());
        maskedPage.setPages(page.getPages());
        return ResultUtils.success(maskedPage);
    }

    @PostMapping("/query/interface/{interfaceId}")
    @Operation(summary = "根据接口ID查询异常记录")
    public BaseResponse<Page<ExceptionRecordMasked>> queryByInterfaceId(@PathVariable(name = "interfaceId") @Parameter(description = "接口ID") Long interfaceId,
                                                                        @RequestBody(required = false) @Parameter(description = "分页参数") PageRequest pageRequest, HttpServletRequest request) {
        //一、数据校验
        //1DTO对象是否为空——@PathVariable实现不为空
        //2获得DTO对象分页参数
        if (pageRequest == null)
            pageRequest = new PageRequest();
        long current = pageRequest.getCurrent();
        long pageSize = pageRequest.getPageSize();
        //3请求对象是否为空
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR, "请求为空");
        //4接口是否存在
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(interfaceId);
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.PARAMS_ERROR, "接口不存在");

        //二、权限校验：需要interface的所有者才能看，或者管理员才能看异常相关信息
        User loggedInUser = userService.getLoggedInUser(request);
        ThrowUtils.throwIf(!userService.isAdmin(loggedInUser)
                && !Objects.equals(loggedInUser.getId(), interfaceInfo.getUserid()), ErrorCode.NO_AUTH_ERROR, "无权限查看");

        //三、构建筛选条件并筛选
        QueryWrapper<ExceptionRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("interface_id", interfaceId);
        Page<ExceptionRecord> page = exceptionRecordService.page(new Page<>(current, pageSize), wrapper);

        //四、返回脱敏后结果
        //获得脱敏列表
        List<ExceptionRecordMasked> maskedList = exceptionRecordService.getExceptionRecordMasked(page.getRecords());
        //制作新的page
        Page<ExceptionRecordMasked> maskedPage = new Page<>();
        maskedPage.setRecords(maskedList);
        maskedPage.setCurrent(page.getCurrent());
        maskedPage.setSize(page.getSize());
        maskedPage.setTotal(page.getTotal());
        maskedPage.setPages(page.getPages());
        return ResultUtils.success(maskedPage);
    }

    @PostMapping("/query/accesskey/{accesskey}")
    @Operation(summary = "根据接口访问码查询异常记录")
    public BaseResponse<Page<ExceptionRecordMasked>> queryByInterfaceId(@PathVariable(name = "accesskey") @Parameter(description = "接口访问码") String accesskey,
                                                                        @RequestBody(required = false) @Parameter(description = "分页参数") PageRequest pageRequest, HttpServletRequest request) {
        //一、数据校验
        //1DTO对象是否为空——@PathVariable实现不为空
        //2获得DTO对象分页参数
        if (pageRequest == null)
            pageRequest = new PageRequest();
        long current = pageRequest.getCurrent();
        long pageSize = pageRequest.getPageSize();
        //3请求对象是否为空
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR, "请求为空");
        //4accesskey是否存在
        InterfaceAccess interfaceAccess = interfaceAccessService.getOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", accesskey));
        ThrowUtils.throwIf(interfaceAccess == null, ErrorCode.PARAMS_ERROR, "接口申请信息不存在");

        //二、权限校验：需要accesskey的所有者才能看，或者管理员才能看异常相关信息
        User loggedInUser = userService.getLoggedInUser(request);
        ThrowUtils.throwIf(!userService.isAdmin(loggedInUser)
                && !Objects.equals(loggedInUser.getId(), interfaceAccess.getUserid()), ErrorCode.NO_AUTH_ERROR, "无权限查看");

        //三、构建筛选条件并筛选
        QueryWrapper<ExceptionRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("accesskey", accesskey);
        Page<ExceptionRecord> page = exceptionRecordService.page(new Page<>(current, pageSize), wrapper);

        //四、返回脱敏后结果
        //获得脱敏列表
        List<ExceptionRecordMasked> maskedList = exceptionRecordService.getExceptionRecordMasked(page.getRecords());
        //制作新的page
        Page<ExceptionRecordMasked> maskedPage = new Page<>();
        maskedPage.setRecords(maskedList);
        maskedPage.setCurrent(page.getCurrent());
        maskedPage.setSize(page.getSize());
        maskedPage.setTotal(page.getTotal());
        maskedPage.setPages(page.getPages());
        return ResultUtils.success(maskedPage);
    }
}
