package com.drofff.manageday.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.drofff.manageday.entity.User;
import com.drofff.manageday.exception.MDException;

public class UserContext {

	private UserContext() {}

	public static User getCurrentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if(!authentication.isAuthenticated()) {
			throw new MDException("User is not authorized");
		}
		return (User) authentication.getPrincipal();
	}

}
