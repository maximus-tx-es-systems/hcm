package com.maximus.hcm.dto;

import java.util.ArrayList;
import java.util.List;

public class FunctionalityLookupVO {
	
	private List<StaffInfoVO> staffList = new ArrayList<StaffInfoVO>();
	private List<StaffInfoVO> supStaffList = new ArrayList<StaffInfoVO>();
	private List<String> locationList= new ArrayList<String>();
	private List<RoleVO> rolesList= new ArrayList<RoleVO>();
	private List<StaffInfoVO> qcStaffList = new ArrayList<StaffInfoVO>();
	
	public List<StaffInfoVO> getStaffList() {
		return staffList;
	}
	public void setStaffList(List<StaffInfoVO> staffList) {
		this.staffList = staffList;
	}
	public List<String> getLocationList() {
		return locationList;
	}
	public void setLocationList(List<String> locationList) {
		this.locationList = locationList;
	}
	public List<RoleVO> getRolesList() {
		return rolesList;
	}
	public void setRolesList(List<RoleVO> rolesList) {
		this.rolesList = rolesList;
	}
	public List<StaffInfoVO> getSupStaffList() {
		return supStaffList;
	}
	public void setSupStaffList(List<StaffInfoVO> supStaffList) {
		this.supStaffList = supStaffList;
	}
	public List<StaffInfoVO> getQcStaffList() {
		return qcStaffList;
	}
	public void setQcStaffList(List<StaffInfoVO> qcStaffList) {
		this.qcStaffList = qcStaffList;
	}
	
}
