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

import com.lucasdevx.Mentorly.dto.request.EnrollmentRequestDTO;
import com.lucasdevx.Mentorly.dto.response.EnrollmentResponseDTO;
import com.lucasdevx.Mentorly.service.EnrollmentService;
import com.lucasdevx.Mentorly.service.UserService;

@RestController
@RequestMapping("/enrollments/v1")
public class EnrollmentController {
	
	private EnrollmentService enrollmentService;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public EnrollmentController(EnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}
	
	@PostMapping
	public EnrollmentResponseDTO create(@RequestBody EnrollmentRequestDTO request) {
		logger.info(">>> Initializing the controller's create method.");
		
		EnrollmentResponseDTO response = enrollmentService.create(request);
		
		logger.info(">>> Finishing the controller's create method.");
		
		return response;
	}
	
	@GetMapping("/{id}")
	public EnrollmentResponseDTO findById(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's findById method.");
		
		EnrollmentResponseDTO response = enrollmentService.findById(id);
		
		logger.info(">>> Finishing the controller's create method.");
		
		return response;
	}
	
	@GetMapping
	public List<EnrollmentResponseDTO> findAll() {
		logger.info(">>> Initializing the controller's findAll method.");
		
		List<EnrollmentResponseDTO> responsesDTO = enrollmentService.findAll();
		
		logger.info(">>> Finishing the controller's create method.");
		
		return responsesDTO;
	}
	
	@PutMapping("/{id}")
	public EnrollmentResponseDTO update(@RequestBody EnrollmentRequestDTO request, @PathVariable Long id) {
		logger.info(">>> Initializing the controller's update method.");
		
		EnrollmentResponseDTO response = enrollmentService.update(request, id);
		
		logger.info(">>> Finishing the controller's create method.");
		
		return response;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's delete method.");
		
		enrollmentService.delete(id);
		
		logger.info(">>> Finishing the controller's create method.");
		return ResponseEntity.noContent().build();
	}

}
