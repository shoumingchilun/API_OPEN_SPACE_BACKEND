package com.chilun.apiopenspace.model.dto.InterfaceAccess;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-02-16-12:08
 */
@Data
public class InterfaceApplyRequest {
    @NotNull
    private Long interfaceId;
}
