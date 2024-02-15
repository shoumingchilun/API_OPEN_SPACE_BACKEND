package com.chilun.apiopenspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chilun.apiopenspace.model.Masked.InterfaceInfoMasked;
import com.chilun.apiopenspace.model.entity.InterfaceInfo;
import java.util.List;

/**
 * @author 齿轮
 * @description 针对表【interface_info(用户表信息)】的数据库操作Service
 * @createDate 2024-01-27 14:44:39
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 接口注册
     *
     * @param userid 接口所有者
     * @param requestMethod 请求方式
     * @param requestPath 请求路径
     * @return 接口id
     */
    long InterfaceRegister(Long userid, String requestPath, Integer requestMethod);

    /**
     * 接口状态变更
     *
     * @param id 接口id
     * @param status 期望变更为的状态
     * @return 修改后的接口信息
     */
    InterfaceInfo statusManage(Long id,Integer status);

    /**
     * 接口废除
     *
     * @param id 接口id
     */
    void InterfaceAbolish(Long id);
    /**
     * 获取脱敏的接口信息
     *
     * @param interfaceInfo 待脱敏对象
     * @return 已脱敏对象
     */
    InterfaceInfoMasked getInterfaceInfoMasked(InterfaceInfo interfaceInfo);

    /**
     * 获取脱敏的接口信息
     *
     * @param interfaceInfoList 待脱敏对象集合
     * @return 已脱敏对象集合
     */
    List<InterfaceInfoMasked> getInterfaceInfoMasked(List<InterfaceInfo> interfaceInfoList);
}
