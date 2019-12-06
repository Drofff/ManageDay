package com.drofff.manageday.dto;

import com.drofff.manageday.exception.MDException;

public class ErrorResponse {

	private int status;

	private String message;

	public ErrorResponse() {}

	public static ErrorResponse fromException(MDException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.status = e.getStatus();
		errorResponse.message = e.getMessage();
		return errorResponse;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
