package com.objectfrontier.training.service.exceptionhandling;

public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	int errorCode;
	String errorMessage;

	public AppException(ExceptionCodes code){

		this.errorCode = code.getId();
		this.errorMessage = code.getErrorMessage();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public AppException(String s) {
		super(s);
	}
}