package com.chilun.apiopenspace.model.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * 普通日志消息，记录是否成功，后续可添加扣费信息
 *
 * @author 齿轮
 * @date 2024-02-28-14:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessLogDTO implements Serializable {
    private String accesskey;
    private boolean success;
    private BigDecimal cost;
}
