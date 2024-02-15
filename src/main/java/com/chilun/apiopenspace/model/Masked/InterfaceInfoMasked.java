package com.chilun.apiopenspace.model.Masked;

import lombok.Data;

import java.io.Serializable;

@Data
public class InterfaceInfoMasked implements Serializable {

    private Long id;
    private Long userid;
    private String requestPath;
    private Integer requestMethod;
    private String requestDataType;
    private String requestParameters;
    private String requestExample;
    private String responseStatus;
    private String responseParameters;
    private String responseExample;
    private Integer status;
    private String introduction;
    private Integer isCost;
    private Integer isRestrict;
    private Integer isExceptionHandling;


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
        InterfaceInfoMasked other = (InterfaceInfoMasked) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
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
                && (this.getIsExceptionHandling() == null ? other.getIsExceptionHandling() == null : this.getIsExceptionHandling().equals(other.getIsExceptionHandling()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
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
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userid=").append(userid);
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
        sb.append("]");
        return sb.toString();
    }
}