package com.chilun.apiopenspace.model.Masked;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 齿轮
 * @date 2024-01-24-19:10
 */
@Data
public class UserMasked implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 用户介绍
     */
    private String introduction;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 用户总余额
     */
    private BigDecimal totalBalance;

    /**
     * 用户可用余额
     */
    private BigDecimal unusedBalance;

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
        com.chilun.apiopenspace.model.entity.User other = (com.chilun.apiopenspace.model.entity.User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
                && (this.getUserNickname() == null ? other.getUserNickname() == null : this.getUserNickname().equals(other.getUserNickname()))
                && (this.getIntroduction() == null ? other.getIntroduction() == null : this.getIntroduction().equals(other.getIntroduction()))
                && (this.getRole() == null ? other.getRole() == null : this.getRole().equals(other.getRole()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getTotalBalance() == null ? other.getTotalBalance() == null : this.getTotalBalance().equals(other.getTotalBalance()))
                && (this.getUnusedBalance() == null ? other.getUnusedBalance() == null : this.getUnusedBalance().equals(other.getUnusedBalance()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getUserNickname() == null) ? 0 : getUserNickname().hashCode());
        result = prime * result + ((getIntroduction() == null) ? 0 : getIntroduction().hashCode());
        result = prime * result + ((getRole() == null) ? 0 : getRole().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getTotalBalance() == null) ? 0 : getTotalBalance().hashCode());
        result = prime * result + ((getUnusedBalance() == null) ? 0 : getUnusedBalance().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", userNickname=").append(userNickname);
        sb.append(", introduction=").append(introduction);
        sb.append(", role=").append(role);
        sb.append(", status=").append(status);
        sb.append(", totalBalance=").append(totalBalance);
        sb.append(", unusedBalance=").append(unusedBalance);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
