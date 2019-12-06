package com.drofff.manageday.entity;

import java.util.Comparator;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.drofff.manageday.enums.UserRole;

@Entity
@Table(name = "user_info")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Email(message = "Please, provide valid email address")
	@NotBlank(message = "Email address is required")
	private String username;

	@NotBlank(message = "Password is required")
	@Length(min = 8, message = "Minimal password length is 8 characters")
	private String password;

	@CollectionTable(name = "user_roles")
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<UserRole> authorities;

	private Boolean isActive;

	public User() {}

	public User(Long id, String username, Set<UserRole> authorities) {
		this.id = id;
		this.username = username;
		this.authorities = authorities;
	}

	public User(String username, String password, Set<UserRole> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Set<UserRole> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Set<UserRole> getAuthorities() {
		return authorities;
	}

	public String getHighestAccessLevelAuthorityStr() {
		return authorities.stream()
				.min(Comparator.comparingInt(UserRole::getAccessLevel))
				.orElse(UserRole.getDefault())
				.name();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActive == null ? Boolean.FALSE : isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken() {
		return new UsernamePasswordAuthenticationToken(this, password, authorities);
	}

}
