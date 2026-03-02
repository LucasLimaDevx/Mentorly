package com.lucasdevx.Mentorly.controller;

import java.util.List;

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

import com.lucasdevx.Mentorly.controller.docs.CourseControllerDocs;
import com.lucasdevx.Mentorly.dto.request.CourseRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
import com.lucasdevx.Mentorly.service.CourseService;
import com.lucasdevx.Mentorly.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/courses/v1")
@Tag(name = "Course", description = "Endpoints for managing Course.")
public class CourseController  implements CourseControllerDocs  {
	
	private CourseService courseService;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
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
	public ResponseEntity<EntityModel<CourseResponseDTO>> create(@RequestBody CourseRequestDTO request) {
		logger.info(">>> Initializing the controller's create method.");
		
		EntityModel<CourseResponseDTO> response = courseService.create(request);
		
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
	public ResponseEntity<EntityModel<CourseResponseDTO>> findById(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's findById method.");
		
		EntityModel<CourseResponseDTO> response = courseService.findById(id);
		
		logger.info(">>> Finishing the controller's findById method.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				})
	public ResponseEntity<List<EntityModel<CourseResponseDTO>>> findAll() {
		logger.info(">>> Initializing the controller's findById method.");
		
		List<EntityModel<CourseResponseDTO>> responsesDTO = courseService.findAll();
		
		logger.info(">>> Finishing the controller's findById method.");
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
	public ResponseEntity<EntityModel<CourseResponseDTO>> update(@RequestBody CourseRequestDTO request, @PathVariable Long id) {
		logger.info(">>> Initializing the controller's update method.");
		
		EntityModel<CourseResponseDTO> response = courseService.update(request, id);
		
		logger.info(">>> Finishing the controller's findById method.");
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's findById method.");
		
		courseService.delete(id);
		
		logger.info(">>> Finishing the controller's findById method.");
		
		return ResponseEntity.noContent().build();
	}

}
