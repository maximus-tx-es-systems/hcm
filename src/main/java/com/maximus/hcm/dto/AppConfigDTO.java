package com.maximus.hcm.dto;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class AppConfigDTO {
	
	private String appCode;
	private String status;
	private boolean active;
	private String statusCode;
	private String errSummary;
	private String warningOn;
	private String defaultNodeCode;
	private String displayName;
	private Date lastCheckedOn;
	private boolean notifyByEmail;
	private List<String> vipNodeCodes;
	private List<String> haproxyNodeCodes;
	private List<String> directNodeCodes;
	private Set<String> consolidatedNodeCodes;
	private LinkedHashMap<String, StatusDTO> consolidatedNodeCodeStatusMap;
	
	public AppConfigDTO() {}
	
	public AppConfigDTO(String appCode) {
		this.appCode = appCode;
		this.active = true;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
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

	public String getDefaultNodeCode() {
		return defaultNodeCode;
	}

	public void setDefaultNodeCode(String defaultNodeCode) {
		this.defaultNodeCode = defaultNodeCode;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Date getLastCheckedOn() {
		return lastCheckedOn;
	}

	public void setLastCheckedOn(Date lastCheckedOn) {
		this.lastCheckedOn = lastCheckedOn;
	}

	public boolean isNotifyByEmail() {
		return notifyByEmail;
	}

	public void setNotifyByEmail(boolean notifyByEmail) {
		this.notifyByEmail = notifyByEmail;
	}

	public List<String> getVipNodeCodes() {
		return vipNodeCodes;
	}

	public void setVipNodeCodes(List<String> vipNodeCodes) {
		this.vipNodeCodes = vipNodeCodes;
	}

	public List<String> getHaproxyNodeCodes() {
		return haproxyNodeCodes;
	}

	public void setHaproxyNodeCodes(List<String> haproxyNodeCodes) {
		this.haproxyNodeCodes = haproxyNodeCodes;
	}

	public List<String> getDirectNodeCodes() {
		return directNodeCodes;
	}

	public void setDirectNodeCodes(List<String> directNodeCodes) {
		this.directNodeCodes = directNodeCodes;
	}

	public Set<String> getConsolidatedNodeCodes() {
		return consolidatedNodeCodes;
	}

	public void setConsolidatedNodeCodes(Set<String> consolidatedNodeCodes) {
		this.consolidatedNodeCodes = consolidatedNodeCodes;
	}

	public LinkedHashMap<String, StatusDTO> getConsolidatedNodeCodeStatusMap() {
		return consolidatedNodeCodeStatusMap;
	}

	public void setConsolidatedNodeCodeStatusMap(LinkedHashMap<String, StatusDTO> consolidatedNodeCodeStatusMap) {
		this.consolidatedNodeCodeStatusMap = consolidatedNodeCodeStatusMap;
	}
	
	
}
