package com.maximus.hcm.dto;

public class StaffInfoVO {
	private long employeeId;
	private String empTamId;
	private String startDate;

	private String empFirstName;
	private String empLastName;
	private String empPrimaryLocation;
	private String empRoleCode;
	private String empRoleDescription;

	private long supId;
	private String supTamId;
	private String supFirstName;
	private String supLastName;
	
	private String empEmail;
	private String agentId;	

	public StaffInfoVO() {
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmpTamId() {
		return empTamId;
	}

	public void setEmpTamId(String empTamId) {
		this.empTamId = empTamId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEmpFirstName() {
		return empFirstName;
	}

	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	public String getEmpLastName() {
		return empLastName;
	}

	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}

	public String getEmpPrimaryLocation() {
		return empPrimaryLocation;
	}

	public void setEmpPrimaryLocation(String empPrimaryLocation) {
		this.empPrimaryLocation = empPrimaryLocation;
	}

	public long getSupId() {
		return supId;
	}

	public void setSupId(long supId) {
		this.supId = supId;
	}

	public String getSupTamId() {
		return supTamId;
	}

	public void setSupTamId(String supTamId) {
		this.supTamId = supTamId;
	}

	public String getSupFirstName() {
		return supFirstName;
	}

	public void setSupFirstName(String supFirstName) {
		this.supFirstName = supFirstName;
	}

	public String getSupLastName() {
		return supLastName;
	}

	public void setSupLastName(String supLastName) {
		this.supLastName = supLastName;
	}

	public String getEmpRoleCode() {
		return empRoleCode;
	}

	public void setEmpRoleCode(String empRoleCode) {
		this.empRoleCode = empRoleCode;
	}

	public String getEmpRoleDescription() {
		return empRoleDescription;
	}

	public void setEmpRoleDescription(String empRoleDescription) {
		this.empRoleDescription = empRoleDescription;
	}
	
	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("employeeId( ").append(this.employeeId).append(" ), ");
		sb.append("empLastName( ").append(this.empLastName).append(" ), ");
		sb.append("empFirstName( ").append(this.empFirstName).append(" ), ");
		sb.append("empRoleCode( ").append(this.empRoleCode).append(" ), ");
		sb.append("empRoleDescription( ").append(this.empRoleDescription).append(" ), ");
		sb.append("empTamId( ").append(this.empTamId).append(" ), ");
		sb.append("startDate( ").append(this.startDate).append(" ), ");
		sb.append("empPrimaryLocation( ").append(this.empPrimaryLocation).append(" ), ");
		sb.append("supId( ").append(this.supId).append(" ), ");
		sb.append("supTamId( ").append(this.supTamId).append(" ), ");
		sb.append("supFirstName( ").append(this.supFirstName).append(" ), ");
		sb.append("supLastName( ").append(this.supLastName).append(" ),");
		sb.append("empEmail( ").append(this.empEmail).append(" ),");
		sb.append("agentId( ").append(this.agentId).append(" )");
		return sb.toString();
	}
}
