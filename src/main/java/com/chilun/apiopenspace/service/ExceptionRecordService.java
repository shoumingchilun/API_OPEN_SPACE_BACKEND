package com.chilun.apiopenspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chilun.apiopenspace.model.Masked.ExceptionRecordMasked;
import com.chilun.apiopenspace.model.entity.ExceptionRecord;

import java.util.List;

/**
 * 提供记录异常功能
 *
 * @author 齿轮
 * @description 针对表【exception_record(异常记录表)】的数据库操作Service
 * @createDate 2024-02-27 15:17:22
 */
public interface ExceptionRecordService extends IService<ExceptionRecord> {
    Long recordException(String accesskey,long userid,long interfaceId, String errorReason, String errorResponse, String errorRequest);
    ExceptionRecordMasked getExceptionRecordMasked(ExceptionRecord exceptionRecord);
    List<ExceptionRecordMasked> getExceptionRecordMasked(List<ExceptionRecord> exceptionRecordList);
}
