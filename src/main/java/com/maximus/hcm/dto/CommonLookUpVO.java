package com.maximus.hcm.dto;

import java.util.ArrayList;
import java.util.List;

public class CommonLookUpVO {

	private FunctionalityLookupVO ccLookupVo;
	private FunctionalityLookupVO opsLookupVo;
	private List<RoleVO> allRolesList = new ArrayList<RoleVO>();
	private List<String> allLocationList = new ArrayList<String>();
	private List<StaffInfoVO> qcStaffList = new ArrayList<StaffInfoVO>();
	
	public FunctionalityLookupVO getCcLookupVo() {
		return ccLookupVo;
	}
	public void setCcLookupVo(FunctionalityLookupVO ccLookupVo) {
		this.ccLookupVo = ccLookupVo;
	}
	public FunctionalityLookupVO getOpsLookupVo() {
		return opsLookupVo;
	}
	public void setOpsLookupVo(FunctionalityLookupVO opsLookupVo) {
		this.opsLookupVo = opsLookupVo;
	}
	public List<RoleVO> getAllRolesList() {
		return allRolesList;
	}
	public void setAllRolesList(List<RoleVO> allRolesList) {
		this.allRolesList = allRolesList;
	}
	public List<String> getAllLocationList() {
		return allLocationList;
	}
	public void setAllLocationList(List<String> allLocationList) {
		this.allLocationList = allLocationList;
	}
	public List<StaffInfoVO> getQcStaffList() {
		return qcStaffList;
	}
	public void setQcStaffList(List<StaffInfoVO> qcStaffList) {
		this.qcStaffList = qcStaffList;
	}
	
}
