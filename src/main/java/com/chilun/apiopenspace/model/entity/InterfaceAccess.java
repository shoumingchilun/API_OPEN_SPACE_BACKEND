package com.chilun.apiopenspace.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 接口申请表
 * @TableName interface_access
 */
@TableName(value ="interface_access")
@Data
public class InterfaceAccess implements Serializable {
    /**
     * 访问码
     */
    @TableId
    private String accesskey;

    /**
     * 校验类型
     */
    private Integer verifyType;

    /**
     * 密钥
     */
    private String secretkey;

    /**
     * 剩余金额
     */
    private BigDecimal remainingAmount;

    /**
     * 单次调用价格
     */
    private BigDecimal cost;

    /**
     * 过期时间
     */
    private Date expiration;

    /**
     * 接口id
     */
    private Long interfaceId;

    /**
     * 所属用户id
     */
    private Long userid;

    /**
     * 总调用次数
     */
    private Integer callTimes;

    /**
     * 失败调用次数
     */
    private Integer failedCallTimes;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
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
                && (this.getSecretkey() == null ? other.getSecretkey() == null : this.getSecretkey().equals(other.getSecretkey()))
                && (this.getRemainingAmount() == null ? other.getRemainingAmount() == null : this.getRemainingAmount().equals(other.getRemainingAmount()))
                && (this.getCost() == null ? other.getCost() == null : this.getCost().equals(other.getCost()))
                && (this.getExpiration() == null ? other.getExpiration() == null : this.getExpiration().equals(other.getExpiration()))
                && (this.getInterfaceId() == null ? other.getInterfaceId() == null : this.getInterfaceId().equals(other.getInterfaceId()))
                && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
                && (this.getCallTimes() == null ? other.getCallTimes() == null : this.getCallTimes().equals(other.getCallTimes()))
                && (this.getFailedCallTimes() == null ? other.getFailedCallTimes() == null : this.getFailedCallTimes().equals(other.getFailedCallTimes()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAccesskey() == null) ? 0 : getAccesskey().hashCode());
        result = prime * result + ((getVerifyType() == null) ? 0 : getVerifyType().hashCode());
        result = prime * result + ((getSecretkey() == null) ? 0 : getSecretkey().hashCode());
        result = prime * result + ((getRemainingAmount() == null) ? 0 : getRemainingAmount().hashCode());
        result = prime * result + ((getCost() == null) ? 0 : getCost().hashCode());
        result = prime * result + ((getExpiration() == null) ? 0 : getExpiration().hashCode());
        result = prime * result + ((getInterfaceId() == null) ? 0 : getInterfaceId().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getCallTimes() == null) ? 0 : getCallTimes().hashCode());
        result = prime * result + ((getFailedCallTimes() == null) ? 0 : getFailedCallTimes().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
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
        sb.append(", secretkey=").append(secretkey);
        sb.append(", remainingAmount=").append(remainingAmount);
        sb.append(", cost=").append(cost);
        sb.append(", expiration=").append(expiration);
        sb.append(", interfaceId=").append(interfaceId);
        sb.append(", userid=").append(userid);
        sb.append(", callTimes=").append(callTimes);
        sb.append(", failedCallTimes=").append(failedCallTimes);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}