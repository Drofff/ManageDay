package com.drofff.manageday.service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.drofff.manageday.cache.ActivationTokenCache;
import com.drofff.manageday.controller.RegistrationController;
import com.drofff.manageday.entity.User;
import com.drofff.manageday.enums.UserRole;
import com.drofff.manageday.exception.MDException;
import com.drofff.manageday.repository.UserRepository;
import com.drofff.manageday.utils.FormattingUtils;
import com.drofff.manageday.utils.ValidationUtils;

@Service
@PropertySource("classpath:/messages.properties")
public class RegistrationService {

	private static final String ACTIVATION_LINK_VARIABLE_NAME = "link";

	private final UserRepository userRepository;
	private final MailService mailService;
	private final PasswordEncoder passwordEncoder;

	@Value("${mail.activation.topic}")
	private String activationMailTopic;

	@Value("${mail.activation.text}")
	private String activationMailText;

	@Value("${server.host.url}")
	private String serverUrl;

	@Autowired
	public RegistrationService(UserRepository userRepository, MailService mailService,
	                           PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.mailService = mailService;
		this.passwordEncoder = passwordEncoder;
	}

	public void registerUser(String username, String password) {
		validateUsernameIsUnique(username);
		User user = buildUserObject(username, password);
		ValidationUtils.validate(user);
		encodeUserPassword(user);
		userRepository.save(user);
		String activationToken = assignRandomActivationTokenToUser(user);
		sendActivationTokenToUser(activationToken, user);
	}

	private void validateUsernameIsUnique(String username) {
		Optional<User> userOptional = userRepository.findByUsername(username);
		if(userOptional.isPresent()) {
			throw new MDException("User with such username already exists");
		}
	}

	private User buildUserObject(String username, String password) {
		UserRole defaultUserRole = UserRole.getDefault();
		Set<UserRole> authorities = Collections.singleton(defaultUserRole);
		return new User(username, password, authorities);
	}

	private void encodeUserPassword(User user) {
		String rawPassword = user.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);
	}

	private String assignRandomActivationTokenToUser(User user) {
		String randomToken = UUID.randomUUID().toString();
		ActivationTokenCache.saveTokenForUser(randomToken, user);
		return randomToken;
	}

	private void sendActivationTokenToUser(String activationToken, User user) {
		String activationLink = generateActivationLink(user.getId(), activationToken);
		Map<String, String> messageVars = Collections.singletonMap(ACTIVATION_LINK_VARIABLE_NAME, activationLink);
		String message = FormattingUtils.putVarsIntoText(messageVars, activationMailText);
		mailService.send(activationMailTopic, message, user.getUsername());
	}

	private String generateActivationLink(Long userId, String activationToken) {
		String activationUri = RegistrationController.ACTIVATE_ACCOUNT_ENDPOINT_MAPPING;
		activationUri = activationUri.replace("{" + RegistrationController.USER_ID_PATH_VARIABLE_NAME + "}", userId.toString());
		activationUri = activationUri.replace("{" + RegistrationController.TOKEN_PATH_VARIABLE_NAME + "}", activationToken);
		return serverUrl + activationUri;
	}

	public void activateAccount(Long userId, String activationToken) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new MDException("User with such id do not exists"));
		String originalToken = ActivationTokenCache.getTokenForUser(user)
				.orElseThrow(() -> new MDException("There are no activation requests for this user"));
		if(!activationToken.equals(originalToken)) {
			throw new MDException("Invalid activation token");
		}
		user.setActive(true);
		userRepository.save(user);
		ActivationTokenCache.invalidateTokenForUser(user);
	}

}
