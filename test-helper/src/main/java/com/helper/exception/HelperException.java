package com.helper.exception;

public class HelperException extends RuntimeException{
	
	private static final long serialVersionUID = -2632420946745243576L;
	private String message = "Something went wrong";
	
	public HelperException(Exception exception) {
		super(exception);
	}
	
	public HelperException(String message) {
		super(new Exception(message));
		this.message = message;
	}
	
	public HelperException(String message, Exception exception) {
		super(exception);
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		StringBuilder builder = new StringBuilder();
		builder.append(message);
		builder.append(": ");
		builder.append(super.getMessage());
		return builder.toString();
	}
}
