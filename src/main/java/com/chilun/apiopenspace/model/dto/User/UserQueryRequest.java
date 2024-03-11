package com.chilun.apiopenspace.model.dto.User;

import com.chilun.apiopenspace.model.dto.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 齿轮
 * @date 2024-01-24-19:22
 * <p>
 * ByID、ByUsername；（all）；likeNickname、likeIntroduce、inRole、inStatus、ge/leTotalBalance
 */
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    @Length(max = 16)
    @Schema(description = "用户昵称")
    private String userNickname;
    @Length(max = 1000)
    @Schema(description = "用户简介")
    private String introduction;
    @Schema(description = "用户角色")
    private Integer[] role;
    @Schema(description = "用户状态")
    private Integer[] status;
    @PositiveOrZero
    @Schema(description = "用户总余额范围（大于等于）")
    private BigDecimal HighTotalBalance;
    @PositiveOrZero
    @Schema(description = "用户总余额范围（小于等于）")
    private BigDecimal LowTotalBalance;
}
