package com.lucasdevx.Mentorly.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdevx.Mentorly.config.TokenConfig;
import com.lucasdevx.Mentorly.dto.request.LoginRequestDTO;
import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.LoginResponseDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.model.User;
import com.lucasdevx.Mentorly.service.UserService;

import jakarta.transaction.Transactional;



@RestController
@RequestMapping("/auth/v1")
public class AuthenticationController {
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final TokenConfig tokenConfig;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	public AuthenticationController(UserService userService, AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.tokenConfig = tokenConfig;
	}
	
	@Transactional
	@PostMapping(
			value = "/login",
			consumes = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE
				},
			produces = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE
				}
			)
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request){
		UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
		Authentication authentication = authenticationManager.authenticate(usernamePasswordToken);
		
		User user = (User) authentication.getPrincipal();
		
		String token = tokenConfig.generateToken(user);
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping(
			value = "/register",
			consumes = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE
				},
			produces = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE
				})
	public ResponseEntity<EntityModel<UserResponseDTO>> register(@RequestBody UserRequestDTO request){
		logger.info(">>> Initializing the controller's register method.");
		
		EntityModel<UserResponseDTO> response = userService.create(request);
		
		logger.info(">>> Finishing the controller's register method.");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
