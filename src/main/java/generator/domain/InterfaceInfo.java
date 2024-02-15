package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 接口信息表
 * @TableName interface_info
 */
@TableName(value ="interface_info")
@Data
public class InterfaceInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 请求路径
     */
    private String requestPath;

    /**
     * 请求方式
     */
    private Integer requestMethod;

    /**
     * 请求数据类型
     */
    private String requestDataType;

    /**
     * 请求参数
     */
    private String requestParameters;

    /**
     * 请求示例
     */
    private String requestExample;

    /**
     * 响应状态
     */
    private String responseStatus;

    /**
     * 响应参数
     */
    private String responseParameters;

    /**
     * 响应示例
     */
    private String responseExample;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 接口介绍
     */
    private String introduction;

    /**
     * 是否开启扣费
     */
    private Integer isCost;

    /**
     * 是否开启请求限制
     */
    private Integer isRestrict;

    /**
     * 是否开启异常处理
     */
    private Integer isExceptionHandling;

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
        InterfaceInfo other = (InterfaceInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRequestPath() == null ? other.getRequestPath() == null : this.getRequestPath().equals(other.getRequestPath()))
            && (this.getRequestMethod() == null ? other.getRequestMethod() == null : this.getRequestMethod().equals(other.getRequestMethod()))
            && (this.getRequestDataType() == null ? other.getRequestDataType() == null : this.getRequestDataType().equals(other.getRequestDataType()))
            && (this.getRequestParameters() == null ? other.getRequestParameters() == null : this.getRequestParameters().equals(other.getRequestParameters()))
            && (this.getRequestExample() == null ? other.getRequestExample() == null : this.getRequestExample().equals(other.getRequestExample()))
            && (this.getResponseStatus() == null ? other.getResponseStatus() == null : this.getResponseStatus().equals(other.getResponseStatus()))
            && (this.getResponseParameters() == null ? other.getResponseParameters() == null : this.getResponseParameters().equals(other.getResponseParameters()))
            && (this.getResponseExample() == null ? other.getResponseExample() == null : this.getResponseExample().equals(other.getResponseExample()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIntroduction() == null ? other.getIntroduction() == null : this.getIntroduction().equals(other.getIntroduction()))
            && (this.getIsCost() == null ? other.getIsCost() == null : this.getIsCost().equals(other.getIsCost()))
            && (this.getIsRestrict() == null ? other.getIsRestrict() == null : this.getIsRestrict().equals(other.getIsRestrict()))
            && (this.getIsExceptionHandling() == null ? other.getIsExceptionHandling() == null : this.getIsExceptionHandling().equals(other.getIsExceptionHandling()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRequestPath() == null) ? 0 : getRequestPath().hashCode());
        result = prime * result + ((getRequestMethod() == null) ? 0 : getRequestMethod().hashCode());
        result = prime * result + ((getRequestDataType() == null) ? 0 : getRequestDataType().hashCode());
        result = prime * result + ((getRequestParameters() == null) ? 0 : getRequestParameters().hashCode());
        result = prime * result + ((getRequestExample() == null) ? 0 : getRequestExample().hashCode());
        result = prime * result + ((getResponseStatus() == null) ? 0 : getResponseStatus().hashCode());
        result = prime * result + ((getResponseParameters() == null) ? 0 : getResponseParameters().hashCode());
        result = prime * result + ((getResponseExample() == null) ? 0 : getResponseExample().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIntroduction() == null) ? 0 : getIntroduction().hashCode());
        result = prime * result + ((getIsCost() == null) ? 0 : getIsCost().hashCode());
        result = prime * result + ((getIsRestrict() == null) ? 0 : getIsRestrict().hashCode());
        result = prime * result + ((getIsExceptionHandling() == null) ? 0 : getIsExceptionHandling().hashCode());
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
        sb.append(", requestPath=").append(requestPath);
        sb.append(", requestMethod=").append(requestMethod);
        sb.append(", requestDataType=").append(requestDataType);
        sb.append(", requestParameters=").append(requestParameters);
        sb.append(", requestExample=").append(requestExample);
        sb.append(", responseStatus=").append(responseStatus);
        sb.append(", responseParameters=").append(responseParameters);
        sb.append(", responseExample=").append(responseExample);
        sb.append(", status=").append(status);
        sb.append(", introduction=").append(introduction);
        sb.append(", isCost=").append(isCost);
        sb.append(", isRestrict=").append(isRestrict);
        sb.append(", isExceptionHandling=").append(isExceptionHandling);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}