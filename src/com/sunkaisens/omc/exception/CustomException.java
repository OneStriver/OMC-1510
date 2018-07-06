package com.sunkaisens.omc.exception;
/**
 *自定义异常类
 */
public class CustomException extends Exception {
	
	private static final long serialVersionUID = 1L;
	public String message;
	
	public CustomException(Throwable e){
		super(e);
		message=e.getMessage();
	}
	
	public CustomException(String message){
		super(message);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
