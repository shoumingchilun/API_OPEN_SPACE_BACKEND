package com.chilun.apiopenspace.model.dto.InterfaceAccess;

import com.chilun.apiopenspace.model.dto.PageRequest;
import lombok.Data;

/**
 * @author 齿轮
 * @date 2024-02-16-13:51
 */
@Data
public class AccesskeyQueryRequest extends PageRequest {
    private Long interfaceId;
    private Long userid;
}
