package com.maximus.hcm.dto;

import java.io.Serializable;

public class HealthCheckDTO implements Serializable{
	
	private static final long serialVersionUID = 3626644129212870247L;
	
	private int statusCode;
	private String statusMessage;
	private String errorMessage;
	private String monitoringHost;
	private String monitoringUrl;
	
	public HealthCheckDTO() {
		
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getMonitoringHost() {
		return monitoringHost;
	}
	public void setMonitoringHost(String monitoringHost) {
		this.monitoringHost = monitoringHost;
	}
	public String getMonitoringUrl() {
		return monitoringUrl;
	}
	public void setMonitoringUrl(String monitoringUrl) {
		this.monitoringUrl = monitoringUrl;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HealthCheckDTO [statusCode=").append(statusCode).append(", statusMessage=")
				.append(statusMessage).append(", errorMessage=").append(errorMessage).append(", monitoringHost=")
				.append(monitoringHost).append(", monitoringUrl=").append(monitoringUrl).append("]");
		return builder.toString();
	}

}
