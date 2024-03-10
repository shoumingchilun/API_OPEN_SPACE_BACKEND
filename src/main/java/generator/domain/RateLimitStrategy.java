package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 接口访问RPS限制表
 * @TableName rate_limit_strategy
 */
@TableName(value ="rate_limit_strategy")
@Data
public class RateLimitStrategy implements Serializable {
    /**
     * 接口id
     */
    @TableId
    private Long id;

    /**
     * 令牌生成速度
     */
    private Integer replenishRate;

    /**
     * 令牌桶大小
     */
    private Integer burstCapacity;

    /**
     * 每个请求消耗令牌
     */
    private Integer requestedTokens;

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
        RateLimitStrategy other = (RateLimitStrategy) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getReplenishRate() == null ? other.getReplenishRate() == null : this.getReplenishRate().equals(other.getReplenishRate()))
            && (this.getBurstCapacity() == null ? other.getBurstCapacity() == null : this.getBurstCapacity().equals(other.getBurstCapacity()))
            && (this.getRequestedTokens() == null ? other.getRequestedTokens() == null : this.getRequestedTokens().equals(other.getRequestedTokens()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getReplenishRate() == null) ? 0 : getReplenishRate().hashCode());
        result = prime * result + ((getBurstCapacity() == null) ? 0 : getBurstCapacity().hashCode());
        result = prime * result + ((getRequestedTokens() == null) ? 0 : getRequestedTokens().hashCode());
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
        sb.append(", replenishRate=").append(replenishRate);
        sb.append(", burstCapacity=").append(burstCapacity);
        sb.append(", requestedTokens=").append(requestedTokens);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}