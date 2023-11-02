package com.maximus.hcm.dto;

import java.io.Serializable;

public class KeyValueDTO implements Serializable{
	
	private static final long serialVersionUID = 86643456545667727L;
	
	private String key;
	private String value;
	
	public KeyValueDTO() {
	}
	
	public KeyValueDTO(String key, String value) {
		this.key = key;
		this.value = value;
	}	
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "{ \"key\":"+key+", \"value\" = "+value+" }";
	}

}
