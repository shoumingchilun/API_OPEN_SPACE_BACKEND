package com.chilun.apiopenspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chilun.apiopenspace.model.Masked.InterfaceAccessMasked;
import com.chilun.apiopenspace.model.entity.InterfaceAccess;

import java.util.List;

/**
 * @author 齿轮
 * @description 针对表【interface_access(接口申请表)】的数据库操作Service
 * @createDate 2024-02-15 20:36:55
 */
public interface InterfaceAccessService extends IService<InterfaceAccess> {
    /**
     * 接口申请
     *
     * @param interfaceId 被申请接口id
     * @return 接口详情，包含访问码和密钥
     */
    InterfaceAccess InterfaceApply(Long userid, Long interfaceId);

    /**
     * 访问码废除
     *
     * @param accesskey 访问码
     */
    void accesskeyAbolish(String accesskey);

    /**
     * 改变验证形式，默认为强校验
     *
     * @param type 访问码
     */
    InterfaceAccess changeVerifyType(String accesskey, Integer type);

    /**
     * 获取脱敏的接口信息
     *
     * @param interfaceAccess 待脱敏对象
     * @return 已脱敏对象
     */
    InterfaceAccessMasked getInterfaceAccessMasked(InterfaceAccess interfaceAccess);

    /**
     * 获取脱敏的接口信息
     *
     * @param interfaceAccessList 待脱敏对象集合
     * @return 已脱敏对象集合
     */
    List<InterfaceAccessMasked> getInterfaceAccessMasked(List<InterfaceAccess> interfaceAccessList);
}
