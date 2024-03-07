package com.crdc.desafio.exception;

public class NegocioException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1020915133222738590L;
	
	private String message;
		
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public NegocioException(String message) {
		this.message = message;		
	}	

}
