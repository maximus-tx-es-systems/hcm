package com.maximus.hcm.dto;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class AppConfigDTO {
	
	private String appCode;
	private String status;
	private String statusCode;
	private String errSummary;
	private String warningOn;
	private String defaultAppCode;
	private String displayName;
	private Date lastCheckedOn;
	private boolean notifyByEmail;
	private List<String> vipAppCodes;
	private List<String> haproxyAppCodes;
	private List<String> directAppCodes;
	private Set<String> consolidatedAppCodes;
	private LinkedHashMap<String, StatusDTO> consolidatedAppCodeStatusMap;
	
	public AppConfigDTO() {}
	
	public AppConfigDTO(String appCode) {
		this.appCode = appCode;
	}
	
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDefaultAppCode() {
		return defaultAppCode;
	}

	public void setDefaultAppCode(String defaultAppCode) {
		this.defaultAppCode = defaultAppCode;
	}

	public List<String> getVipAppCodes() {
		return vipAppCodes;
	}
	public void setVipAppCodes(List<String> vipAppCodes) {
		this.vipAppCodes = vipAppCodes;
	}
	public List<String> getHaproxyAppCodes() {
		return haproxyAppCodes;
	}
	public void setHaproxyAppCodes(List<String> haproxyAppCodes) {
		this.haproxyAppCodes = haproxyAppCodes;
	}
	public List<String> getDirectAppCodes() {
		return directAppCodes;
	}
	public void setDirectAppCodes(List<String> directAppCodes) {
		this.directAppCodes = directAppCodes;
	}
	public Set<String> getConsolidatedAppCodes() {
		return consolidatedAppCodes;
	}
	public void setConsolidatedAppCodes(Set<String> consolidatedAppCodes) {
		this.consolidatedAppCodes = consolidatedAppCodes;
	}

	public String getErrSummary() {
		return errSummary;
	}

	public void setErrSummary(String errSummary) {
		this.errSummary = errSummary;
	}

	public String getWarningOn() {
		return warningOn;
	}

	public void setWarningOn(String warningOn) {
		this.warningOn = warningOn;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public LinkedHashMap<String, StatusDTO> getConsolidatedAppCodeStatusMap() {
		return consolidatedAppCodeStatusMap;
	}

	public void setConsolidatedAppCodeStatusMap(LinkedHashMap<String, StatusDTO> consolidatedAppCodeStatusMap) {
		this.consolidatedAppCodeStatusMap = consolidatedAppCodeStatusMap;
	}

	public Date getLastCheckedOn() {
		return lastCheckedOn;
	}

	public void setLastCheckedOn(Date lastCheckedOn) {
		this.lastCheckedOn = lastCheckedOn;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isNotifyByEmail() {
		return notifyByEmail;
	}

	public void setNotifyByEmail(boolean notifyByEmail) {
		this.notifyByEmail = notifyByEmail;
	}
	
}
