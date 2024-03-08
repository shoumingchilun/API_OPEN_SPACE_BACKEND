package com.chilun.apiopenspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.constant.CostTypeValue;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.mapper.InterfaceChargeStrategyMapper;
import com.chilun.apiopenspace.model.entity.InterfaceChargeStrategy;
import com.chilun.apiopenspace.model.entity.InterfaceInfo;
import com.chilun.apiopenspace.service.InterfaceChargeStrategyService;
import com.chilun.apiopenspace.service.InterfaceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author 齿轮
 * @description 针对表【interface_charge_strategy(接口收费策略表)】的数据库操作Service实现
 * @createDate 2024-03-08 14:06:05
 */
@Service
public class InterfaceChargeStrategyServiceImpl extends ServiceImpl<InterfaceChargeStrategyMapper, InterfaceChargeStrategy>
        implements InterfaceChargeStrategyService {

    @Resource(name = "InterfaceInfoService")
    InterfaceInfoService interfaceInfoService;


    @Override
    public InterfaceChargeStrategy alterInterfaceChargeStrategy(Long id, Integer costType, BigDecimal fixedFee, Long fixedTime) {
        //一、参数校验
        //1空值校验
        ThrowUtils.throwIf(id == null || costType == null, ErrorCode.PARAMS_ERROR, "接口id或策略为空！");
        ThrowUtils.throwIf(costType > 2 || costType < 0, ErrorCode.PARAMS_ERROR, "策略类型不存在！");
        ThrowUtils.throwIf(costType == CostTypeValue.FIXED_FEE && fixedFee == null, ErrorCode.PARAMS_ERROR, "固定费用策略下，固定费用不能为空！");
        ThrowUtils.throwIf(costType == CostTypeValue.FIXED_TIME && fixedTime == null, ErrorCode.PARAMS_ERROR, "固定时长策略下，固定时长不能为空！");
        //2有效性检验
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.PARAMS_ERROR, "指定接口不存在！");
        //3范围校验
        ThrowUtils.throwIf(fixedFee != null &&
                        (fixedFee.compareTo(new BigDecimal(1000)) > 0 || fixedFee.compareTo(new BigDecimal(0)) < 0),
                ErrorCode.PARAMS_ERROR, "固定费用范围为0-1000！");
        ThrowUtils.throwIf(fixedTime != null &&
                        (fixedTime < 1 || fixedTime > 15552000),
                ErrorCode.PARAMS_ERROR, "时间区间为1分钟-30年！");

        //二、进行策略注册或更新
        InterfaceChargeStrategy strategy = new InterfaceChargeStrategy();
        strategy.setId(id);
        strategy.setCostType(costType);
        strategy.setFixedFee(fixedFee);
        strategy.setFixedTime(fixedTime);

        boolean b = saveOrUpdate(strategy);
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR, "保存或更新收费策略失败！");

        //三、开启接口收费设置
        interfaceInfo.setIsCost(1);
        boolean b1 = interfaceInfoService.updateById(interfaceInfo);
        ThrowUtils.throwIf(!b1, ErrorCode.SYSTEM_ERROR, "开启接口收费失败！");

        //四、返回结果
        return getById(id);
    }

    @Override
    public void deleteInterfaceChargeStrategy(Long id) {
        //一、参数校验
        //1空值校验
        ThrowUtils.throwIf(id == null, ErrorCode.PARAMS_ERROR, "接口id为空！");
        //2有效性检验
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.PARAMS_ERROR, "指定接口不存在！");

        //二、删除策略
        if (interfaceInfo.getIsCost() == 1) {
            interfaceInfo.setIsCost(0);
            boolean b = interfaceInfoService.updateById(interfaceInfo);
            ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR, "关闭接口收费失败！");
        }
        if (getById(id) != null) {
            boolean b = removeById(id);
            ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR, "删除收费策略失败！");
        }
    }
}




