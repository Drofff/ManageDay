package com.drofff.manageday.cache;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.drofff.manageday.entity.User;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class ActivationTokenCache {

	private static final Cache<String, String> CACHE = Caffeine.newBuilder()
			.expireAfterWrite(1, TimeUnit.HOURS)
			.build();

	public static void saveTokenForUser(String token, User user) {
		String username = user.getUsername();
		CACHE.put(username, token);
	}

	public static Optional<String> getTokenForUser(User user) {
		String username = user.getUsername();
		String token = CACHE.getIfPresent(username);
		return Optional.ofNullable(token);
	}

	public static void invalidateTokenForUser(User user) {
		String username = user.getUsername();
		CACHE.invalidate(username);
	}

}