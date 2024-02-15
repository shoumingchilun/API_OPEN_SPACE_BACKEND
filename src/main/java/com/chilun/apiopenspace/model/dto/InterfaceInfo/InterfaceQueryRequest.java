package com.chilun.apiopenspace.model.dto.InterfaceInfo;

import com.chilun.apiopenspace.model.dto.PageRequest;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author 齿轮
 * @date 2024-01-28-10:30
 *
 * byUserid、likeRequestPath、inRequestMethod、inStatus、likeIntroduction、isCost、isRestrict、isExceptionHandling
 */
@Data
public class InterfaceQueryRequest extends PageRequest {
    private Long userid;

    @Length(max = 200,min = 1)
    private String  likeRequestPath;

    private Integer[] inRequestMethod;

    private Integer[] inStatus;

    @Length(max = 250)
    private String likeIntroduction;

    @Max(1)
    @Min(0)
    private Integer isCost;

    @Max(1)
    @Min(0)
    private Integer isRestrict;

    @Max(1)
    @Min(0)
    private Integer isExceptionHandling;
}
