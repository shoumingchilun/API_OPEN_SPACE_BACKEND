package com.chilun.apiopenspace.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chilun.apiopenspace.Utils.ThrowUtils;
import com.chilun.apiopenspace.exception.ErrorCode;
import com.chilun.apiopenspace.mapper.ExceptionRecordMapper;
import com.chilun.apiopenspace.model.Masked.ExceptionRecordMasked;
import com.chilun.apiopenspace.model.dto.message.ErrorLogDTO;
import com.chilun.apiopenspace.model.entity.ExceptionRecord;
import com.chilun.apiopenspace.model.entity.InterfaceAccess;
import com.chilun.apiopenspace.service.ExceptionRecordService;
import com.chilun.apiopenspace.service.InterfaceAccessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于记录异常情况的服务层
 * <p>
 * 注意：校验accesskey是否有效其实意义不大，因为网关转发请求前accesskey的有效性已经校验过了，只有有效的情况下，才可能发出异常日志消息
 *
 * @author 齿轮
 * @description 针对表【exception_record(异常记录表)】的数据库操作Service实现
 * @createDate 2024-02-27 15:17:22
 */
@Service
public class ExceptionRecordServiceImpl extends ServiceImpl<ExceptionRecordMapper, ExceptionRecord>
        implements ExceptionRecordService {

    @Resource
    InterfaceAccessService interfaceAccessService;

    @Override
    public ExceptionRecord getExceptionRecordByErrorLogDTO(ErrorLogDTO dto) {
        ExceptionRecord exceptionRecord = new ExceptionRecord();
        exceptionRecord.setAccesskey(dto.getAccesskey());
        exceptionRecord.setUserid(dto.getUserid());
        exceptionRecord.setInterfaceId(dto.getInterfaceId());
        exceptionRecord.setErrorReason(dto.getErrorReason());
        exceptionRecord.setErrorRequest(dto.getRequest());
        exceptionRecord.setErrorResponse(dto.getResponse());
        return exceptionRecord;
    }

    @Override
    public Long recordException(String accesskey, long userid, long interfaceId, String errorReason, String errorResponse, String errorRequest) {
        //一、参数校验——不校验

        //二、进行保存
        //1新建对象并赋值
        ExceptionRecord exceptionRecord = new ExceptionRecord();
        exceptionRecord.setAccesskey(accesskey);
        exceptionRecord.setUserid(userid);
        exceptionRecord.setInterfaceId(interfaceId);
        exceptionRecord.setErrorRequest(errorRequest);
        exceptionRecord.setErrorResponse(errorResponse);
        exceptionRecord.setErrorReason(errorReason);
        //2保存对象
        boolean save = save(exceptionRecord);
        ThrowUtils.throwIf(!save, ErrorCode.SYSTEM_ERROR, "保存异常记录失败");

        //三、返回ID
        return exceptionRecord.getId();
    }

    @Override
    public ExceptionRecordMasked getExceptionRecordMasked(ExceptionRecord exceptionRecord) {
        if (exceptionRecord == null)
            return null;
        ExceptionRecordMasked exceptionRecordMasked = new ExceptionRecordMasked();
        BeanUtils.copyProperties(exceptionRecord, exceptionRecordMasked);
        return exceptionRecordMasked;
    }

    @Override
    public List<ExceptionRecordMasked> getExceptionRecordMasked(List<ExceptionRecord> exceptionRecordList) {
        List<ExceptionRecordMasked> list = new ArrayList<>();
        for (ExceptionRecord exceptionRecord : exceptionRecordList) {
            list.add(getExceptionRecordMasked(exceptionRecord));
        }
        return list;
    }
}




