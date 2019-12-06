package com.drofff.manageday.exception;

public class MDException extends RuntimeException {

	private int status = 400;

	public MDException() {
		super();
	}

	public MDException(String message) {
		super(message);
	}

	public MDException(String message, int status) {
		super(message);
		this.status = status;
	}

	public MDException(String message, Throwable cause) {
		super(message, cause);
	}

	public MDException(Throwable cause) {
		super(cause);
	}

	protected MDException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public int getStatus() {
		return status;
	}

}
