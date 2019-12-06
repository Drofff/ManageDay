package com.drofff.manageday.dto;

import java.util.Map;

import com.drofff.manageday.exception.ValidationException;

public class ValidationErrorResponse extends ErrorResponse {

	private Map<String, String> fieldErrors;

	public static ValidationErrorResponse fromException(ValidationException e) {
		ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
		validationErrorResponse.setMessage(e.getMessage());
		validationErrorResponse.setStatus(e.getStatus());
		validationErrorResponse.setFieldErrors(e.getFieldErrors());
		return validationErrorResponse;
	}

	public Map<String, String> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(Map<String, String> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}
