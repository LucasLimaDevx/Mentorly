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
import com.lucasdevx.Mentorly.controller.docs.LessonControllerDocs;
import com.lucasdevx.Mentorly.dto.request.LessonRequestDTO;
import com.lucasdevx.Mentorly.dto.response.LessonResponseDTO;
import com.lucasdevx.Mentorly.service.LessonService;
import com.lucasdevx.Mentorly.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/lessons/v1")
@Tag(name = "Lesson", description = "Endpoints for managing Lesson.")
public class LessonController  implements LessonControllerDocs {
	
	private LessonService lessonService;
	private TokenConfig tokenConfig;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public LessonController(LessonService lessonService, TokenConfig tokenConfig) {
		this.lessonService = lessonService;
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
	public ResponseEntity<EntityModel<LessonResponseDTO>> create(@RequestBody LessonRequestDTO request) {
		logger.info(">>> Initializing the controller's create method.");
		
		EntityModel<LessonResponseDTO> response = lessonService.create(request);
		
		logger.info(">>> Finishing the controller's create method.");
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping(
			value = "/{id}",
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				})
	public ResponseEntity<EntityModel<LessonResponseDTO>> findById(@PathVariable Long id, HttpServletRequest httpRequest) {
		logger.info(">>> Initializing the controller's findById method.");
		
		String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
		Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
		String role = optUser.get().role();
		
		if(id <= 0) {
			throw new IllegalArgumentException("The ID provided is not valid.");
		}
		
		EntityModel<LessonResponseDTO> response = lessonService.findById(id, role);
		
		logger.info(">>> Finishing the controller's findById method.");
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				})
	public ResponseEntity<List<EntityModel<LessonResponseDTO>>> findAll(HttpServletRequest httpRequest) {
		logger.info(">>> Initializing the controller's findAll method.");
		
		String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
		Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
		String role = optUser.get().role();
		
		List<EntityModel<LessonResponseDTO>> responsesDTO = lessonService.findAll(role);
		
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
						}
			)
	public ResponseEntity<EntityModel<LessonResponseDTO>> update(@RequestBody LessonRequestDTO request, @PathVariable Long id) {
		logger.info(">>> Initializing the controller's update method.");
		
		if(id <= 0) {
			throw new IllegalArgumentException("The ID provided is not valid.");
		}
		
		EntityModel<LessonResponseDTO> response = lessonService.update(request, id);
		
		logger.info(">>> Finishing the controller's update method.");
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's delete method.");
		
		if(id <= 0) {
			throw new IllegalArgumentException("The ID provided is not valid.");
		}
		
		lessonService.delete(id);
		
		logger.info(">>> Finishing the controller's delete method.");
		
		return ResponseEntity.noContent().build();
	}

}
