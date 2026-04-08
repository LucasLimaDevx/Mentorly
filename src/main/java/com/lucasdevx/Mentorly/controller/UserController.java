package com.lucasdevx.Mentorly.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdevx.Mentorly.config.JWTUserData;
import com.lucasdevx.Mentorly.config.TokenConfig;
import com.lucasdevx.Mentorly.controller.docs.UserControllerDocs;
import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users/v1")
@Tag(name = "User", description = "Endpoints for managing User.")
public class UserController implements UserControllerDocs {
	
	private UserService userService;
	private TokenConfig tokenConfig;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public UserController(UserService userService, TokenConfig tokenConfig) {
		this.userService = userService;
		this.tokenConfig = tokenConfig;
	}
	
	@PostMapping(
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
	public ResponseEntity<EntityModel<UserResponseDTO> > create(@RequestBody UserRequestDTO request) {
		logger.info(">>> Initializing the controller's create method.");
		
		EntityModel<UserResponseDTO> response = userService.create(request, null);
		
		logger.info(">>> Finishing the controller's create method.");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping(
			value = "/{id}",
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
		})
	public ResponseEntity<EntityModel<UserResponseDTO>> findById(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's findById method.");
		
		if(id <= 0) {
			throw new IllegalArgumentException("The ID provided is not valid.");
		}
		
		EntityModel<UserResponseDTO> response = userService.findById(id);
		
		logger.info(">>> Finishing the controller's findById method.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(
			value = "/me",
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
		})
	public ResponseEntity<EntityModel<UserResponseDTO>> findAuthStudent(HttpServletRequest httpRequest) {
		logger.info(">>> Initializing the controller's findAuthUser method.");
		
		String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
		Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
		
		Long id = optUser.get().userId();
		
		EntityModel<UserResponseDTO> response = userService.findById(id);
		
		logger.info(">>> Finishing the controller's findAuthUser method.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				})
	public ResponseEntity<List<EntityModel<UserResponseDTO>>> findAll() {
		logger.info(">>> Initializing the controller's findAll method.");
		
		List<EntityModel<UserResponseDTO>> responsesDTO = userService.findAll(null);
		
		logger.info(">>> Finishing the controller's findAll method.");
		
		return ResponseEntity.ok(responsesDTO);
	}
	
	@PutMapping(
			value = "/{id}",
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
	public ResponseEntity<EntityModel<UserResponseDTO>> update(@RequestBody UserRequestDTO request, @PathVariable Long id) {
		logger.info(">>> Initializing the controller's update method.");
		/*
		String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
		Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
		String role = optUser.get().role();*/
		
		
		if(id <= 0) {
			throw new IllegalArgumentException("The ID provided is not valid.");
		}
		
		EntityModel<UserResponseDTO> response = userService.update(request, id, null);
		
		logger.info(">>> Finishing the controller's update method.");
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping(
			value = "/me",
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
	public ResponseEntity<EntityModel<UserResponseDTO>> updateAuthStudent(@RequestBody UserRequestDTO request, HttpServletRequest httpRequest) {
		logger.info(">>> Initializing the controller's updateAuthUser method.");
		
		String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
		Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
		
		Long id = optUser.get().userId();
		String role = optUser.get().role();
		
		EntityModel<UserResponseDTO> response = userService.update(request, id, role);
		
		logger.info(">>> Finishing the controller's updateAuthUser method.");
		
		return ResponseEntity.ok(response);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's delete method.");
		
		if(id <= 0) {
			throw new IllegalArgumentException("The ID provided is not valid.");
		}
		userService.delete(id);
		
		logger.info(">>> Finishing the controller's delete method.");
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/me")
	public ResponseEntity<Void> deleteAuthStudent(HttpServletRequest httpRequest) {
		logger.info(">>> Initializing the controller's deleteAuthUser method.");
		
		String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
		Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
		
		Long id = optUser.get().userId();
		
		userService.delete(id);
		
		logger.info(">>> Finishing the controller's deleteAuthUser method.");
		return ResponseEntity.noContent().build();
	}
}
