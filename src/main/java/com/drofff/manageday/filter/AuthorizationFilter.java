package com.drofff.manageday.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.drofff.manageday.constants.AuthenticationConstants;
import com.drofff.manageday.entity.User;
import com.drofff.manageday.utils.JwtUtils;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private final JwtUtils jwtUtils;

	public AuthorizationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		super(authenticationManager);
		this.jwtUtils = jwtUtils;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String authenticationToken = request.getHeader(AuthenticationConstants.AUTHENTICATION_TOKEN_HEADER_NAME);
		Optional<User> userOptional = parseToken(authenticationToken);
		userOptional.ifPresent(this::authorizeUser);
		chain.doFilter(request, response);
	}

	private Optional<User> parseToken(String token) {
		try {
			User user = jwtUtils.getUserFromToken(token);
			return Optional.of(user);
		} catch(JWTDecodeException e) {
			return Optional.empty();
		}
	}

	private void authorizeUser(User user) {
		UsernamePasswordAuthenticationToken authenticationToken = user.toUsernamePasswordAuthenticationToken();
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

}
