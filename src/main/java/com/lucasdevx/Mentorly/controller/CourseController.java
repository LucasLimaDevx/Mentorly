package com.lucasdevx.Mentorly.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdevx.Mentorly.dto.request.CourseRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
import com.lucasdevx.Mentorly.service.CourseService;
import com.lucasdevx.Mentorly.service.UserService;

@RestController
@RequestMapping("/courses/v1")
public class CourseController {
	
	private CourseService courseService;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}
	
	@PostMapping
	public CourseResponseDTO create(@RequestBody CourseRequestDTO request) {
		logger.info(">>> Initializing the controller's create method.");
		
		CourseResponseDTO response = courseService.create(request);
		
		logger.info(">>> Finishing the controller's create method.");
		
		return response;
	}
	
	@GetMapping("/{id}")
	public CourseResponseDTO findById(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's findById method.");
		
		CourseResponseDTO response = courseService.findById(id);
		
		logger.info(">>> Finishing the controller's findById method.");
		return response;
	}
	
	@GetMapping
	public List<CourseResponseDTO> findAll() {
		logger.info(">>> Initializing the controller's findById method.");
		
		List<CourseResponseDTO> responsesDTO = courseService.findAll();
		
		logger.info(">>> Finishing the controller's findById method.");
		return responsesDTO;
	}
	
	@PutMapping("/{id}")
	public CourseResponseDTO update(@RequestBody CourseRequestDTO request, @PathVariable Long id) {
		logger.info(">>> Initializing the controller's update method.");
		
		CourseResponseDTO response = courseService.update(request, id);
		
		logger.info(">>> Finishing the controller's findById method.");
		
		return response;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's findById method.");
		
		courseService.delete(id);
		
		logger.info(">>> Finishing the controller's findById method.");
		
		return ResponseEntity.noContent().build();
	}

}
