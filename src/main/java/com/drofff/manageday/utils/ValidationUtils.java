package com.drofff.manageday.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.drofff.manageday.entity.Ownable;
import com.drofff.manageday.entity.User;
import com.drofff.manageday.exception.ValidationException;

public class ValidationUtils {

	private static final Validator VALIDATOR;

	static {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		VALIDATOR = validatorFactory.getValidator();
	}

	private ValidationUtils() {}

	public static <T> void validate(T object) {
		Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(object);
		if(!constraintViolations.isEmpty()) {
			Map<String, String> fieldErrors = convertConstraintViolationsToFieldErrorsMap(constraintViolations);
			throw new ValidationException(fieldErrors);
		}
	}

	private static <T> Map<String, String> convertConstraintViolationsToFieldErrorsMap(Set<ConstraintViolation<T>> constraintViolations) {
		Map<String, String> filedErrors = new HashMap<>();
		constraintViolations.forEach(constraintViolation -> {
			String fieldName = constraintViolation.getPropertyPath().toString();
			String errorMessage = constraintViolation.getMessage();
			filedErrors.put(fieldName, errorMessage);
		});
		return filedErrors;
	}

	public static <T extends Ownable> void validateUserIsOwnerOf(User user, T ownable) {
		Long ownerId = ownable.getOwner().getId();
		if(!user.getId().equals(ownerId)) {
			String className = ownable.getClass().getName();
			throw new ValidationException("User is not the owner of the " + className.toLowerCase());
		}
	}

}
