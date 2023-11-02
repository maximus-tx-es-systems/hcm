package com.maximus.hcm.dto;

public class RolePermissionsVO {
	private int roleId;
	private int formId;
	private String formCode;
	private String formLabel;
	private String subFormLabel;
	public RolePermissionsVO() {
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getFormId() {
		return formId;
	}
	public void setFormId(int formId) {
		this.formId = formId;
	}
	public String getFormCode() {
		return formCode;
	}
	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}
	public String getFormLabel() {
		return formLabel;
	}
	public void setFormLabel(String formLabel) {
		this.formLabel = formLabel;
	}
	public String getSubFormLabel() {
		return subFormLabel;
	}
	public void setSubFormLabel(String subFormLabel) {
		this.subFormLabel = subFormLabel;
	}
	public RolePermissionsVO(int roleId, int formId, String formCode,
			String formLabel, String subFormLabel) {
		super();
		this.roleId = roleId;
		this.formId = formId;
		this.formCode = formCode;
		this.formLabel = formLabel;
		this.subFormLabel = subFormLabel;
	}

	

	public boolean equals(Object aThat) {
	    if ( this == aThat ) return true;
	    if ( !(aThat instanceof RolePermissionsVO) ) return false;
	    RolePermissionsVO that = (RolePermissionsVO)aThat;
	    if(this.formLabel!=null){
	    	this.formLabel=this.formLabel.trim();
	    }
	    if(this.subFormLabel!=null){
	    	this.subFormLabel=this.subFormLabel.trim();
	    }

	    if( ((this.formLabel==null) && (that.formLabel!=null) )|| 
	    		((this.formLabel!=null) && (that.formLabel==null))){
	    	return false;
	    }
	    if( ((this.subFormLabel==null) && (that.subFormLabel!=null) )|| 
	    		((this.subFormLabel!=null) && (that.subFormLabel==null))){
	    	return false;
	    }
	    
	    return ( (this.formLabel.equals(that.formLabel) && (this.subFormLabel.equals(that.subFormLabel))));
	}
	
}
