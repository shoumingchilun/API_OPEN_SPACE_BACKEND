package com.chilun.apiopenspace.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 异常记录表
 * @TableName exception_record
 */
@TableName(value ="exception_record")
@Data
public class ExceptionRecord implements Serializable {
    /**
     * 异常ID
     */
    @TableId
    private Long id;

    /**
     * accesskey
     */
    private String accesskey;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 接口ID
     */
    private Long interfaceId;

    /**
     * 异常原因
     */
    private String errorReason;

    /**
     * 异常响应体
     */
    private String errorResponse;

    /**
     * 异常请求信息
     */
    private String errorRequest;

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
        ExceptionRecord other = (ExceptionRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAccesskey() == null ? other.getAccesskey() == null : this.getAccesskey().equals(other.getAccesskey()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getInterfaceId() == null ? other.getInterfaceId() == null : this.getInterfaceId().equals(other.getInterfaceId()))
            && (this.getErrorReason() == null ? other.getErrorReason() == null : this.getErrorReason().equals(other.getErrorReason()))
            && (this.getErrorResponse() == null ? other.getErrorResponse() == null : this.getErrorResponse().equals(other.getErrorResponse()))
            && (this.getErrorRequest() == null ? other.getErrorRequest() == null : this.getErrorRequest().equals(other.getErrorRequest()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccesskey() == null) ? 0 : getAccesskey().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getInterfaceId() == null) ? 0 : getInterfaceId().hashCode());
        result = prime * result + ((getErrorReason() == null) ? 0 : getErrorReason().hashCode());
        result = prime * result + ((getErrorResponse() == null) ? 0 : getErrorResponse().hashCode());
        result = prime * result + ((getErrorRequest() == null) ? 0 : getErrorRequest().hashCode());
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
        sb.append(", id=").append(id);
        sb.append(", accesskey=").append(accesskey);
        sb.append(", userid=").append(userid);
        sb.append(", interfaceId=").append(interfaceId);
        sb.append(", errorReason=").append(errorReason);
        sb.append(", errorResponse=").append(errorResponse);
        sb.append(", errorRequest=").append(errorRequest);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}