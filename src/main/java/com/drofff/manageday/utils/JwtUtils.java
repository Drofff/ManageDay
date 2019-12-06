package com.drofff.manageday.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.drofff.manageday.constants.AuthenticationConstants;
import com.drofff.manageday.entity.User;
import com.drofff.manageday.enums.UserRole;

@Component
public class JwtUtils {

	private static final Duration TOKEN_VALIDITY_DURATION = Duration.of(2, ChronoUnit.HOURS);
	private static final String ID_CLAIM_KEY = "ID";
	private static final String ROLE_CLAIM_KEY = "Role";
	private static final Charset CHARSET = StandardCharsets.ISO_8859_1;

	private Algorithm signAlgorithm;

	@Value("${jwt.key}")
	private String secretKey;

	@PostConstruct
	public void init() {
		byte [] secretKeyBytes = secretKey.getBytes(CHARSET);
		signAlgorithm = Algorithm.HMAC512(secretKeyBytes);
	}

	public String generateTokenForUser(User user) {
		String jwtToken = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(calculateExpirationDate())
				.withClaim(ID_CLAIM_KEY, user.getId())
				.withClaim(ROLE_CLAIM_KEY, user.getHighestAccessLevelAuthorityStr())
				.sign(signAlgorithm);
		return AuthenticationConstants.AUTHENTICATION_TOKEN_PREFIX + jwtToken;
	}

	private Date calculateExpirationDate() {
		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime expirationDateTime = now.plus(TOKEN_VALIDITY_DURATION);
		return Date.from(expirationDateTime.toInstant());
	}

	public User getUserFromToken(String token) {
		validateTokenIsNotNull(token);
		String jwtToken = removeTokenPrefix(token);
		DecodedJWT decodedJWT = JWT.decode(jwtToken);
		Long userId = decodedJWT.getClaim(ID_CLAIM_KEY).asLong();
		String username = decodedJWT.getSubject();
		String highestAuthority = decodedJWT.getClaim(ROLE_CLAIM_KEY).asString();
		Set<UserRole> authorities = authoritiesStrToUserRoleSet(highestAuthority);
		return new User(userId, username, authorities);
	}

	private void validateTokenIsNotNull(String token) {
		if(Objects.isNull(token)) {
			throw new JWTDecodeException("Token is null");
		}
	}

	private String removeTokenPrefix(String jwtToken) {
		return jwtToken.replace(AuthenticationConstants.AUTHENTICATION_TOKEN_PREFIX, "");
	}

	private Set<UserRole> authoritiesStrToUserRoleSet(String authorities) {
		UserRole highestAuthority = UserRole.valueOf(authorities);
		return Collections.singleton(highestAuthority);
	}

}
