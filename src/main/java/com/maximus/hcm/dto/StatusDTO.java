package com.maximus.hcm.dto;

public class StatusDTO {
 
	private String appCode;
	private String nodeCode;
	private String nodeCodeUri;
//	private String appCodeUri;
	private String statusCode;
	private String status;
	private String warningOn;
	private String error;
	
	private boolean active;
	
	private boolean notifyByEmail;
	
	public StatusDTO() {
		active = true; 
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getNodeCodeUri() {
		return nodeCodeUri;
	}

	public void setNodeCodeUri(String nodeCodeUri) {
		this.nodeCodeUri = nodeCodeUri;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isNotifyByEmail() {
		return notifyByEmail;
	}

	public void setNotifyByEmail(boolean notifyByEmail) {
		this.notifyByEmail = notifyByEmail;
	}
	
	
}
