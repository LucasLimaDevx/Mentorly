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
import com.lucasdevx.Mentorly.controller.docs.EnrollmentControllerDocs;
import com.lucasdevx.Mentorly.dto.request.EnrollmentRequestDTO;
import com.lucasdevx.Mentorly.dto.response.EnrollmentResponseDTO;
import com.lucasdevx.Mentorly.service.EnrollmentService;
import com.lucasdevx.Mentorly.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/enrollments/v1")
@Tag(name = "Enrollment", description = "Endpoints for managing Enrollment.")
public class EnrollmentController implements EnrollmentControllerDocs {
	
	private EnrollmentService enrollmentService;
	private TokenConfig tokenConfig;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public EnrollmentController(EnrollmentService enrollmentService, TokenConfig tokenConfig) {
		this.enrollmentService = enrollmentService;
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
				}
			)
	public ResponseEntity<EntityModel<EnrollmentResponseDTO>> create(@RequestBody EnrollmentRequestDTO request, HttpServletRequest httpRequest) {
		logger.info(">>> Initializing the controller's create method.");
		
		String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
		Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
		
		Long userId = optUser.get().userId();
		String role = optUser.get().role();
		
		EntityModel<EnrollmentResponseDTO> response = enrollmentService.create(request, userId, role);
		
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
	public ResponseEntity<EntityModel<EnrollmentResponseDTO>> findById(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's findById method.");
		
		if(id <= 0) {
			throw new IllegalArgumentException("The ID provided is not valid.");
		}
		
		EntityModel<EnrollmentResponseDTO> response = enrollmentService.findById(id);
		
		logger.info(">>> Finishing the controller's create method.");
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				}
			)
	public ResponseEntity<List<EntityModel<EnrollmentResponseDTO>>> findAll() {
		logger.info(">>> Initializing the controller's findAll method.");
		
		List<EntityModel<EnrollmentResponseDTO>> responsesDTO = enrollmentService.findAll(null, null);
		
		logger.info(">>> Finishing the controller's create method.");
		
		return ResponseEntity.ok(responsesDTO);
	}
	
	@GetMapping(
			value = "/me",
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				}
			)
	public ResponseEntity<List<EntityModel<EnrollmentResponseDTO>>> findAllEnrollmentAuthUser(HttpServletRequest httpRequest) {
		logger.info(">>> Initializing the controller's findAll method.");
		
		String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
		Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
		Long userId = optUser.get().userId();
		String role = optUser.get().role();
		
		List<EntityModel<EnrollmentResponseDTO>> responsesDTO = enrollmentService.findAll(userId, role);
		
		logger.info(">>> Finishing the controller's create method.");
		
		return ResponseEntity.ok(responsesDTO);
	}
	
	@PutMapping(
			value="/{id}",
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
	public ResponseEntity<EntityModel<EnrollmentResponseDTO>> update(
			@RequestBody EnrollmentRequestDTO request, Long id) {
		logger.info(">>> Initializing the controller's update method.");
		
		if(id <= 0 ) {
			throw new IllegalArgumentException("The ID provided is not valid.");
		}
		
		EntityModel<EnrollmentResponseDTO> response = enrollmentService.update(request, id);
		
		logger.info(">>> Finishing the controller's create method.");
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's delete method.");
		
		if(id <= 0) {
			throw new IllegalArgumentException("The ID provided is not valid.");
		}
		
		enrollmentService.delete(id);
		
		logger.info(">>> Finishing the controller's create method.");
		return ResponseEntity.noContent().build();
	}

}
