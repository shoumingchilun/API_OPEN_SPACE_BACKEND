package com.chilun.apiopenspace.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.mapper.InterfaceAccessMapper;
import com.chilun.apiopenspace.model.Masked.InterfaceAccessMasked;
import com.chilun.apiopenspace.model.entity.InterfaceAccess;
import com.chilun.apiopenspace.service.InterfaceAccessService;
import com.chilun.apiopenspace.service.InterfaceInfoService;
import com.chilun.apiopenspace.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 齿轮
 * @description 针对表【interface_access(接口申请表)】的数据库操作Service实现
 * @createDate 2024-02-15 20:36:55
 */
@Service
public class InterfaceAccessServiceImpl extends ServiceImpl<InterfaceAccessMapper, InterfaceAccess>
        implements InterfaceAccessService {

    @Resource
    UserService userService;
    @Resource(name = "InterfaceInfoService")
    InterfaceInfoService interfaceInfoService;
    @Resource
    InterfaceAccessMapper interfaceAccessMapper;

    @Override
    public InterfaceAccess InterfaceApply(Long userid, Long interfaceId) {
        //一、校验数据格式
        //1是否为空
        ThrowUtils.throwIf(userid == null, ErrorCode.PARAMS_ERROR, "用户ID未指定");
        ThrowUtils.throwIf(interfaceId == null, ErrorCode.PARAMS_ERROR, "申请接口ID未指定");

        //二、校验用户、接口是否存在
        ThrowUtils.throwIf(userService.getById(userid) == null, ErrorCode.PARAMS_ERROR, "用户不存在");
        ThrowUtils.throwIf(interfaceInfoService.getById(interfaceId) == null, ErrorCode.PARAMS_ERROR, "申请接口不存在");

        //三、进行申请
        //1获得待注册接口申请类
        InterfaceAccess interfaceAccess = new InterfaceAccess();
        interfaceAccess.setInterfaceId(interfaceId);
        interfaceAccess.setUserid(userid);
        //2生成accesskey、secretkey
        String accesskey = UUID.randomUUID().toString();
        String secretkey = UUID.randomUUID().toString();
        interfaceAccess.setAccesskey(accesskey);
        interfaceAccess.setSecretkey(secretkey);

        //2进行申请注册
        boolean save = save(interfaceAccess);
        ThrowUtils.throwIf(!save, ErrorCode.SYSTEM_ERROR, "申请失败");
        //3返回注册结果
        return this.getBaseMapper().selectOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", accesskey));
    }

    @Override
    public void accesskeyAbolish(String accesskey) {
        //一、校验数据格式
        //1是否为空
        ThrowUtils.throwIf(accesskey == null, ErrorCode.PARAMS_ERROR, "accesskey访问码未指定");

        //二、检验申请是否存在
        InterfaceAccess interfaceAccess = this.getOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", accesskey));
        ThrowUtils.throwIf(interfaceAccess == null, ErrorCode.PARAMS_ERROR, "接口申请不存在");

        //三、删除端口
        boolean remove = this.remove(new QueryWrapper<InterfaceAccess>().eq("accesskey", accesskey));
        ThrowUtils.throwIf(!remove, ErrorCode.SYSTEM_ERROR, "撤销接口申请失败");
    }

    @Override
    public InterfaceAccess changeVerifyType(String accesskey, Integer type) {
        //一、校验数据格式
        //1是否为空
        ThrowUtils.throwIf(accesskey == null, ErrorCode.PARAMS_ERROR, "访问码未指定");
        ThrowUtils.throwIf(type == null, ErrorCode.PARAMS_ERROR, "期望状态未指定");
        //2数值校验
        ThrowUtils.throwIf(type > 1 || type < 0, ErrorCode.PARAMS_ERROR, "错误的状态参数错误");

        //二、检验接口是否存在
        InterfaceAccess interfaceAccess = this.getOne(new QueryWrapper<InterfaceAccess>().eq("accesskey", accesskey));
        ThrowUtils.throwIf(interfaceAccess == null, ErrorCode.PARAMS_ERROR, "接口不存在");

        //三、更新接口状态
        interfaceAccess.setVerifyType(type);
        boolean b = update(interfaceAccess, new QueryWrapper<InterfaceAccess>().eq("accesskey", accesskey));
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR, "更新失败");

        //返回结果
        return interfaceAccess;
    }

    @Override
    public InterfaceAccessMasked getInterfaceAccessMasked(InterfaceAccess interfaceAccess) {
        if (interfaceAccess == null)
            return null;
        InterfaceAccessMasked interfaceAccessMasked = new InterfaceAccessMasked();
        BeanUtils.copyProperties(interfaceAccess, interfaceAccessMasked);
        return interfaceAccessMasked;
    }

    @Override
    public List<InterfaceAccessMasked> getInterfaceAccessMasked(List<InterfaceAccess> interfaceAccessList) {
        List<InterfaceAccessMasked> interfaceAccessMaskedList = new ArrayList<>();
        for (InterfaceAccess interfaceAccess : interfaceAccessList) {
            interfaceAccessMaskedList.add(getInterfaceAccessMasked(interfaceAccess));
        }
        return interfaceAccessMaskedList;
    }

    @Override
    public void addCallTimes(int callTimes, int failedCallTimes, String accesskey) {
        interfaceAccessMapper.addCallTimes(callTimes, failedCallTimes, accesskey);
    }
}