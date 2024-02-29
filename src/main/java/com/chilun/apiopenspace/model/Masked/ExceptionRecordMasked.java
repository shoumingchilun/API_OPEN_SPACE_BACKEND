package com.chilun.apiopenspace.model.Masked;

import lombok.Data;

import java.io.Serializable;

/**
 * 异常记录表
 * @TableName exception_record
 */
@Data
public class ExceptionRecordMasked implements Serializable {
    private Long id;
    private String accesskey;
    private Long userid;
    private Long interfaceId;
    private String errorReason;
    private String errorResponse;
    private String errorRequest;
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
        ExceptionRecordMasked other = (ExceptionRecordMasked) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAccesskey() == null ? other.getAccesskey() == null : this.getAccesskey().equals(other.getAccesskey()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getInterfaceId() == null ? other.getInterfaceId() == null : this.getInterfaceId().equals(other.getInterfaceId()))
            && (this.getErrorReason() == null ? other.getErrorReason() == null : this.getErrorReason().equals(other.getErrorReason()))
            && (this.getErrorResponse() == null ? other.getErrorResponse() == null : this.getErrorResponse().equals(other.getErrorResponse()))
            && (this.getErrorRequest() == null ? other.getErrorRequest() == null : this.getErrorRequest().equals(other.getErrorRequest()));
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}