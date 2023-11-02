package com.maximus.hcm.dto;

import java.util.ArrayList;

public class RoleVO {
	private int roleId;
	private String roleCode;
	private String roleName;
	private String externalRoleName;
	private ArrayList<RolePermissionsVO> permissionList;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getExternalRoleName() {
		return externalRoleName;
	}

	public void setExternalRoleName(String externalRoleName) {
		this.externalRoleName = externalRoleName;
	}

	public ArrayList<RolePermissionsVO> getPermissionList() {
		if (permissionList == null) {
			permissionList = new ArrayList<RolePermissionsVO>();
		}
		return permissionList;
	}

	public void setPermissionList(ArrayList<RolePermissionsVO> permissionList) {
		this.permissionList = permissionList;
	}

	public void addPermission(RolePermissionsVO permission) {
		this.getPermissionList().add(permission);
	}

	public void addPermissions(ArrayList<RolePermissionsVO> permissions) {
		this.getPermissionList().addAll(permissions);
	}

	public RoleVO() {
	}

	public RoleVO(int roleId, String roleCode, String roleName, String externalRoleName) {
		this.roleId = roleId;
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.externalRoleName = externalRoleName;
	}

	public RoleVO(int roleId, String roleCode, String roleName) {
		this.roleId = roleId;
		this.roleCode = roleCode;
		this.roleName = roleName;
	}

	public RoleVO(String roleCode, String roleName) {
		this.roleCode = roleCode;
		this.roleName = roleName;
	}

	@Override
	public boolean equals(Object aThat) {
		if (this == aThat)
			return true;
		if (!(aThat instanceof RoleVO))
			return false;
		RoleVO that = (RoleVO) aThat;
		return (this.roleId == that.roleId);
	}

}
