package com.crdc.desafio.enums;

public enum StatusEnum {

	SUCCESS("success"),
	ERROR("error");
	
	private final String status;
	
	StatusEnum(String status){
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
}
