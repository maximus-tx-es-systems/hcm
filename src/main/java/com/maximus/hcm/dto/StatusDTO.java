package com.maximus.hcm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusDTO {
 
	//@JsonProperty("App Code")
	private String appCode;
//	@JsonProperty("App Health Check URL")
	private String appCodeUri;
//	@JsonProperty("Health Check Status Code")
	private String statusCode;
//	@JsonProperty("Health Check Status")
	private String status;
//	@JsonProperty("App Health Check Warnings")
	private String warningOn;
//	@JsonProperty("App Health check Errors")
	private String error;
	
	private boolean notifyByEmail;
	
	public String getAppCodeUri() {
		return appCodeUri;
	}
	public void setAppCodeUri(String appCodeUri) {
		this.appCodeUri = appCodeUri;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWarningOn() {
		return warningOn;
	}
	public void setWarningOn(String warningOn) {
		this.warningOn = warningOn;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public boolean isNotifyByEmail() {
		return notifyByEmail;
	}
	public void setNotifyByEmail(boolean notifyByEmail) {
		this.notifyByEmail = notifyByEmail;
	}
	
}
