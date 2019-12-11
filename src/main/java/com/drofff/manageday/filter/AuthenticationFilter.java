package com.drofff.manageday.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.drofff.manageday.constants.AuthenticationConstants;
import com.drofff.manageday.entity.User;
import com.drofff.manageday.utils.JwtUtils;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

	private static final String JSON_CONTENT_TYPE = "application/json";
	private static final String INVALID_CREDENTIALS_MESSAGE_JSON = "{ \"message\" : \"Provided credentials are invalid\" }";

	private final JwtUtils jwtUtils;

	public AuthenticationFilter(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	                                        FilterChain chain, Authentication authResult) {
		User user = (User) authResult.getPrincipal();
		String authenticationToken = jwtUtils.generateTokenForUser(user);
		response.setHeader(AuthenticationConstants.AUTHENTICATION_TOKEN_HEADER_NAME, authenticationToken);
		LOG.info("Successfully authenticated user with username=[{}] and IP address=[{}]", user.getUsername(), request.getRemoteAddr());
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	                                          AuthenticationException failed) throws IOException {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setContentType(JSON_CONTENT_TYPE);
		PrintWriter printWriter = response.getWriter();
		printWriter.println(INVALID_CREDENTIALS_MESSAGE_JSON);
		LOG.info("Unsuccessful authentication attempt from IP address=[{}]", request.getRemoteAddr());
	}

}
