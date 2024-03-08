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
 * 接口收费策略表
 * @TableName interface_charge_strategy
 */
@TableName(value ="interface_charge_strategy")
@Data
public class InterfaceChargeStrategy implements Serializable {
    /**
     * 接口id
     */
    @TableId
    private Long id;

    /**
     * 收费类型
     */
    private Integer costType;

    /**
     * 单次调用固定费用
     */
    private BigDecimal fixedFee;

    /**
     * 有效时间
     */
    private Long fixedTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
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
        InterfaceChargeStrategy other = (InterfaceChargeStrategy) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCostType() == null ? other.getCostType() == null : this.getCostType().equals(other.getCostType()))
            && (this.getFixedFee() == null ? other.getFixedFee() == null : this.getFixedFee().equals(other.getFixedFee()))
            && (this.getFixedTime() == null ? other.getFixedTime() == null : this.getFixedTime().equals(other.getFixedTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCostType() == null) ? 0 : getCostType().hashCode());
        result = prime * result + ((getFixedFee() == null) ? 0 : getFixedFee().hashCode());
        result = prime * result + ((getFixedTime() == null) ? 0 : getFixedTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", costType=").append(costType);
        sb.append(", fixedFee=").append(fixedFee);
        sb.append(", fixedTime=").append(fixedTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}