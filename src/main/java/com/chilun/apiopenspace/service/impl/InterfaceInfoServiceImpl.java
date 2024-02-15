package com.chilun.apiopenspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.mapper.InterfaceInfoMapper;
import com.chilun.apiopenspace.model.Masked.InterfaceInfoMasked;
import com.chilun.apiopenspace.model.entity.InterfaceInfo;
import com.chilun.apiopenspace.service.InterfaceInfoService;
import com.chilun.apiopenspace.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 齿轮
 * @description 针对表【interface_info(用户表信息)】的数据库操作Service实现
 * @createDate 2024-01-27 14:44:39
 */
@Service("InterfaceInfoService")
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Resource
    UserService userService;

    @Override
    public long InterfaceRegister(Long userid, String requestPath, Integer requestMethod) {
        //一、校验数据格式
        //1是否为空
        ThrowUtils.throwIf(userid == null, ErrorCode.PARAMS_ERROR, "用户ID未指定");
        ThrowUtils.throwIf(StringUtils.isBlank(requestPath), ErrorCode.PARAMS_ERROR, "请求路径未指定");
        ThrowUtils.throwIf(requestMethod == null, ErrorCode.PARAMS_ERROR, "请求方式未指定");
        //2长度校验
        ThrowUtils.throwIf(requestPath.length() > 200, ErrorCode.PARAMS_ERROR, "路径过长");
        //3数值校验
        ThrowUtils.throwIf(requestMethod > 8 || requestMethod < 0, ErrorCode.PARAMS_ERROR, "请求方式参数错误");

        //二、校验用户是否存在
        ThrowUtils.throwIf(userService.getById(userid) == null, ErrorCode.PARAMS_ERROR, "用户不存在");

        //三、进行注册
        //1获得待注册接口类
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setUserid(userid);
        interfaceInfo.setRequestPath(requestPath);
        interfaceInfo.setRequestMethod(requestMethod);
        //2进行注册
        boolean save = save(interfaceInfo);
        ThrowUtils.throwIf(!save, ErrorCode.SYSTEM_ERROR, "注册失败");
        //3返回id
        return interfaceInfo.getId();
    }

    @Override
    public InterfaceInfo statusManage(Long id, Integer status) {
        //一、校验数据格式
        //1是否为空
        ThrowUtils.throwIf(id == null, ErrorCode.PARAMS_ERROR, "端口ID未指定");
        ThrowUtils.throwIf(status == null, ErrorCode.PARAMS_ERROR, "期望状态未指定");
        //2数值校验
        ThrowUtils.throwIf(status > 1 || status < 0, ErrorCode.PARAMS_ERROR, "状态参数错误");

        //二、检验接口是否存在
        InterfaceInfo interfaceInfo = getById(id);
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.PARAMS_ERROR, "接口不存在");

        //三、更新接口状态
        interfaceInfo.setStatus(status);
        boolean b = updateById(interfaceInfo);
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR, "更新失败");

        //返回结果
        return interfaceInfo;
    }

    @Override
    public void InterfaceAbolish(Long id) {
        //一、校验数据格式
        //1是否为空
        ThrowUtils.throwIf(id == null, ErrorCode.PARAMS_ERROR, "端口ID未指定");

        //二、检验接口是否存在
        InterfaceInfo interfaceInfo = getById(id);
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.PARAMS_ERROR, "接口不存在");

        //三、删除端口
        boolean remove = removeById(id);
        ThrowUtils.throwIf(!remove, ErrorCode.SYSTEM_ERROR, "废除接口失败");
    }

    @Override
    public InterfaceInfoMasked getInterfaceInfoMasked(InterfaceInfo interfaceInfo) {
        if (interfaceInfo == null)
            return null;
        InterfaceInfoMasked interfaceInfoMasked = new InterfaceInfoMasked();
        BeanUtils.copyProperties(interfaceInfo, interfaceInfoMasked);
        return interfaceInfoMasked;
    }

    @Override
    public List<InterfaceInfoMasked> getInterfaceInfoMasked(List<InterfaceInfo> interfaceInfoList) {
        List<InterfaceInfoMasked> interfaceInfoMaskedList = new ArrayList<>();
        for (InterfaceInfo interfaceInfo : interfaceInfoList) {
            interfaceInfoMaskedList.add(getInterfaceInfoMasked(interfaceInfo));
        }
        return interfaceInfoMaskedList;
    }
}