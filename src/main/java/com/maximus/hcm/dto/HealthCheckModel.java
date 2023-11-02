package com.maximus.hcm.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maximus.hcm.utils.JsonDefaultDateDeSerializer;
import com.maximus.hcm.utils.JsonDefaultDateSerializer;

public class HealthCheckModel implements Serializable {

	private static final long serialVersionUID = 4146109675571346138L;
	
	private String appCode;
	private String appUrl;
	private String status;
	private String statusCode;
	private String errDescription;
	private int respDelayTime;
	private boolean hasError;
	private boolean hasException;
	@JsonSerialize(using=JsonDefaultDateSerializer.class)
	@JsonDeserialize(using=JsonDefaultDateDeSerializer.class)
	private Date requestTime;
	@JsonSerialize(using=JsonDefaultDateSerializer.class)
	@JsonDeserialize(using=JsonDefaultDateDeSerializer.class)
	private Date responseTime;
	
	private boolean hasNotified;
	private String errSummary;
	
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrDescription() {
		return errDescription;
	}
	public void setErrDescription(String errDescription) {
		this.errDescription = errDescription;
	}
	public int getRespDelayTime() {
		return respDelayTime;
	}
	public void setRespDelayTime(int respDelayTime) {
		this.respDelayTime = respDelayTime;
	}
	public boolean hasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public boolean isHasNotified() {
		return hasNotified;
	}
	public void setHasNotified(boolean hasNotified) {
		this.hasNotified = hasNotified;
	}
	public boolean isHasError() {
		return hasError;
	}
	public boolean isHasException() {
		return hasException;
	}
	public boolean hasException() {
		return hasException;
	}
	public void setHasException(boolean hasException) {
		this.hasException = hasException;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	public String getErrSummary() {
		return errSummary;
	}
	public void setErrSummary(String errSummary) {
		this.errSummary = errSummary;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
}
