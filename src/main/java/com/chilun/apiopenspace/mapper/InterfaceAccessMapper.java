package com.chilun.apiopenspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chilun.apiopenspace.model.entity.InterfaceAccess;
import com.chilun.apiopenspace.service.InterfaceAccessService;
import com.chilun.apiopenspace.service.impl.InterfaceAccessServiceImpl;

import java.util.List;

/**
 * @author 齿轮
 * @description 针对表【interface_access(接口申请表)】的数据库操作Mapper
 * @createDate 2024-02-15 20:36:55
 * @Entity generator.domain.InterfaceAccess
 */
public interface InterfaceAccessMapper extends BaseMapper<InterfaceAccess> {
    void addCallTimes(int callTimes, int failedCallTimes, String accesskey);
    void batchAddCallTimes(List<InterfaceAccessService.BatchAddItem> list);
}




