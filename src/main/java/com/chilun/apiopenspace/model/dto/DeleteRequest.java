package com.chilun.apiopenspace.model.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 齿轮
 * @date 2024-01-26-14:20
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    @NotNull
    @Parameter(description = "实体id")
    private Long id;
}