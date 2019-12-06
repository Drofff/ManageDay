package com.drofff.manageday.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

	ADMIN(1), USER(2);

	private int accessLevel;

	UserRole(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public static UserRole getDefault() {
		return UserRole.USER;
	}

	@Override
	public String getAuthority() {
		return this.name();
	}

	public int getAccessLevel() {
		return accessLevel;
	}

}
