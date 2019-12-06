package com.drofff.manageday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.drofff.manageday.dto.UserDto;
import com.drofff.manageday.service.RegistrationService;

@RestController
public class RegistrationController {

	public static final String USER_ID_PATH_VARIABLE_NAME = "userId";
	public static final String TOKEN_PATH_VARIABLE_NAME = "token";
	public static final String ACTIVATE_ACCOUNT_ENDPOINT_MAPPING = "/accounts/{" + USER_ID_PATH_VARIABLE_NAME +
														"}/activate/{" + TOKEN_PATH_VARIABLE_NAME + "}";

	private final RegistrationService registrationService;

	@Autowired
	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@PostMapping("/registration")
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
		registrationService.registerUser(userDto.getUsername(), userDto.getPassword());
		return ResponseEntity.ok("Successful registration. Please, follow the link in the activation mail we have sent");
	}

	@GetMapping(ACTIVATE_ACCOUNT_ENDPOINT_MAPPING)
	public ResponseEntity<String> activateAccount(@PathVariable(name = USER_ID_PATH_VARIABLE_NAME) Long id,
	                                              @PathVariable(name = TOKEN_PATH_VARIABLE_NAME) String token) {
		registrationService.activateAccount(id, token);
		return ResponseEntity.ok("Account has been successfully activated");
	}

}
