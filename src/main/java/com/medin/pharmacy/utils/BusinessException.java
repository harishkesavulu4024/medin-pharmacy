package com.medin.pharmacy.utils;

public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable throwable) {
		super(throwable);
	}
	
	public BusinessException(String message,Exception e) {
		super(message,e);
	}
}
