package com.chilun.apiopenspace.model.dto.InterfaceInfo;

import com.chilun.apiopenspace.model.dto.PageRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author 齿轮
 * @date 2024-01-28-0:15
 */
@Data
public class InterfaceQueryOthersRequest extends PageRequest {
    @NotNull
    @Length(min = 1, max = 250)
    @Schema(description = "接口简介")
    private String likeIntroduction;
}
