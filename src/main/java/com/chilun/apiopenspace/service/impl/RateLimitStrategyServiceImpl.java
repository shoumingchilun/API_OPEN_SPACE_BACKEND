package com.chilun.apiopenspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.mapper.RateLimitStrategyMapper;
import com.chilun.apiopenspace.model.entity.InterfaceInfo;
import com.chilun.apiopenspace.model.entity.RateLimitStrategy;
import com.chilun.apiopenspace.service.InterfaceInfoService;
import com.chilun.apiopenspace.service.RateLimitStrategyService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 齿轮
* @description 针对表【rate_limit_strategy(接口访问RPS限制表)】的数据库操作Service实现
* @createDate 2024-03-10 17:22:10
*/
@Service("RateLimitStrategyService")
public class RateLimitStrategyServiceImpl extends ServiceImpl<RateLimitStrategyMapper, RateLimitStrategy>
    implements RateLimitStrategyService {
    @Resource(name = "InterfaceInfoService")
    InterfaceInfoService interfaceInfoService;

    @Override
    public RateLimitStrategy alterRateLimitStrategy(Long id, Integer replenishRate, Integer burstCapacity, Integer requestedTokens) {
        //一、参数校验
        //1空值校验
        ThrowUtils.throwIf(ObjectUtils.anyNull(id,replenishRate,burstCapacity,requestedTokens), ErrorCode.PARAMS_ERROR, "接口id或RPS策略参数为空！");

        //2有效性检验
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.PARAMS_ERROR, "指定接口不存在！");

        //二、进行策略注册或更新
        RateLimitStrategy strategy = new RateLimitStrategy();
        strategy.setId(id);
        strategy.setReplenishRate(replenishRate);
        strategy.setBurstCapacity(burstCapacity);
        strategy.setRequestedTokens(requestedTokens);

        boolean b = saveOrUpdate(strategy);
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR, "保存或更新RPS策略失败！");

        //三、开启接口收费设置
        interfaceInfo.setIsRestrict(1);
        boolean b1 = interfaceInfoService.updateById(interfaceInfo);
        ThrowUtils.throwIf(!b1, ErrorCode.SYSTEM_ERROR, "开启接口限制失败！");

        //四、返回结果
        return getById(id);
    }

    @Override
    public void deleteRateLimitStrategy(Long id) {
        //一、参数校验
        //1空值校验
        ThrowUtils.throwIf(id == null, ErrorCode.PARAMS_ERROR, "接口id为空！");
        //2有效性检验
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.PARAMS_ERROR, "指定接口不存在！");

        //二、删除策略
        if (interfaceInfo.getIsCost() == 1) {
            interfaceInfo.setIsRestrict(0);
            boolean b = interfaceInfoService.updateById(interfaceInfo);
            ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR, "关闭接口限制失败！");
        }
        if (getById(id) != null) {
            boolean b = removeById(id);
            ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR, "删除限制策略失败！");
        }
    }
}
