package com.drofff.manageday.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.drofff.manageday.dto.ErrorResponse;
import com.drofff.manageday.dto.ValidationErrorResponse;
import com.drofff.manageday.exception.MDException;
import com.drofff.manageday.exception.ValidationException;

@ControllerAdvice
public class BaseControllerAdvice {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationException(ValidationException e) {
		return ResponseEntity.status(e.getStatus()).body(ValidationErrorResponse.fromException(e));
	}

	@ExceptionHandler(MDException.class)
	public ResponseEntity<ErrorResponse> handleMDException(MDException e) {
		return ResponseEntity.status(e.getStatus()).body(ErrorResponse.fromException(e));
	}

}
