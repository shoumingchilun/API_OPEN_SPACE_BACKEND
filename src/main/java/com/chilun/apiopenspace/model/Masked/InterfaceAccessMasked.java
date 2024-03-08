package com.chilun.apiopenspace.model.Masked;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.chilun.apiopenspace.model.entity.InterfaceAccess;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 齿轮
 * @date 2024-02-15-20:51
 */
@Data
public class InterfaceAccessMasked {

    private String accesskey;
    private Integer verifyType;
    private BigDecimal remainingAmount;
    private BigDecimal cost;
    private Date expiration;
    private Long interfaceId;
    private Long userid;
    private Integer callTimes;
    private Integer failedCallTimes;
    private static final long serialVersionUID = 1L;


    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        InterfaceAccess other = (InterfaceAccess) that;
        return (this.getAccesskey() == null ? other.getAccesskey() == null : this.getAccesskey().equals(other.getAccesskey()))
                && (this.getVerifyType() == null ? other.getVerifyType() == null : this.getVerifyType().equals(other.getVerifyType()))
                && (this.getRemainingAmount() == null ? other.getRemainingAmount() == null : this.getRemainingAmount().equals(other.getRemainingAmount()))
                && (this.getCost() == null ? other.getCost() == null : this.getCost().equals(other.getCost()))
                && (this.getExpiration() == null ? other.getExpiration() == null : this.getExpiration().equals(other.getExpiration()))
                && (this.getInterfaceId() == null ? other.getInterfaceId() == null : this.getInterfaceId().equals(other.getInterfaceId()))
                && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
                && (this.getCallTimes() == null ? other.getCallTimes() == null : this.getCallTimes().equals(other.getCallTimes()))
                && (this.getFailedCallTimes() == null ? other.getFailedCallTimes() == null : this.getFailedCallTimes().equals(other.getFailedCallTimes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAccesskey() == null) ? 0 : getAccesskey().hashCode());
        result = prime * result + ((getVerifyType() == null) ? 0 : getVerifyType().hashCode());
        result = prime * result + ((getRemainingAmount() == null) ? 0 : getRemainingAmount().hashCode());
        result = prime * result + ((getCost() == null) ? 0 : getCost().hashCode());
        result = prime * result + ((getExpiration() == null) ? 0 : getExpiration().hashCode());
        result = prime * result + ((getInterfaceId() == null) ? 0 : getInterfaceId().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getCallTimes() == null) ? 0 : getCallTimes().hashCode());
        result = prime * result + ((getFailedCallTimes() == null) ? 0 : getFailedCallTimes().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", accesskey=").append(accesskey);
        sb.append(", verifyType=").append(verifyType);
        sb.append(", remainingAmount=").append(remainingAmount);
        sb.append(", cost=").append(cost);
        sb.append(", expiration=").append(expiration);
        sb.append(", interfaceId=").append(interfaceId);
        sb.append(", userid=").append(userid);
        sb.append(", callTimes=").append(callTimes);
        sb.append(", failedCallTimes=").append(failedCallTimes);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}
