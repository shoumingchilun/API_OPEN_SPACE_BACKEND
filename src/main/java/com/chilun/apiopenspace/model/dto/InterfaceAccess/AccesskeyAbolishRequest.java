package com.chilun.apiopenspace.model.dto.InterfaceAccess;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-02-16-12:18
 */
@Data
public class AccesskeyAbolishRequest {
    @NotNull
    private String accesskey;
}
