package com.chilun.apiopenspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chilun.apiopenspace.model.entity.InterfaceChargeStrategy;

import java.math.BigDecimal;

/**
* @author 齿轮
* @description 针对表【interface_charge_strategy(接口收费策略表)】的数据库操作Service
* @createDate 2024-03-08 14:06:05
*/
public interface InterfaceChargeStrategyService extends IService<InterfaceChargeStrategy> {
    InterfaceChargeStrategy alterInterfaceChargeStrategy(Long id, Integer costType, BigDecimal fixedFee, Long fixedTime);
    void deleteInterfaceChargeStrategy(Long id);
}
