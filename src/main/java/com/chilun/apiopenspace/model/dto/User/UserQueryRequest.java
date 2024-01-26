package com.chilun.apiopenspace.model.dto.User;

import com.chilun.apiopenspace.model.dto.PageRequest;
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
    private String userNickname;
    @Length(max = 1000)
    private String introduction;
    private Integer[] role;
    private Integer[] status;
    @PositiveOrZero
    private BigDecimal HighTotalBalance;
    @PositiveOrZero
    private BigDecimal LowTotalBalance;
}
