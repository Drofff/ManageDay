package com.drofff.manageday.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends MDException {

	private Map<String, String> fieldErrors = new HashMap<>();

	public ValidationException() {
		super();
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Map<String, String> errors) {
		this.fieldErrors = errors;
	}

	public ValidationException(String message, Map<String, String> errors) {
		super(message);
		this.fieldErrors = errors;
	}

	public ValidationException(String message, int status) {
		super(message, status);
	}

	public ValidationException(String message, int status, Map<String, String> fieldErrors) {
		super(message, status);
		this.fieldErrors = fieldErrors;
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	protected ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public Map<String, String> getFieldErrors() {
		return fieldErrors;
	}
}
