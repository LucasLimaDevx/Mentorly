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

import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.service.UserService;

@RestController
@RequestMapping("/users/v1")
public class UserController {
	
	private UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public UserResponseDTO create(@RequestBody UserRequestDTO request) {
		logger.info(">>> Initializing the controller's create method.");
		
		UserResponseDTO response = userService.create(request);
		
		logger.info(">>> Finishing the controller's create method.");
		return response;
	}
	
	@GetMapping("/{id}")
	public UserResponseDTO findById(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's findById method.");
		
		UserResponseDTO response = userService.findById(id);
		
		logger.info(">>> Finishing the controller's findById method.");
		return response;
	}
	
	@GetMapping
	public List<UserResponseDTO> findAll() {
		logger.info(">>> Initializing the controller's findAll method.");
		
		List<UserResponseDTO> responsesDTO = userService.findAll();
		
		logger.info(">>> Finishing the controller's findAll method.");
		
		return responsesDTO;
	}
	
	@PutMapping("/{id}")
	public UserResponseDTO update(@RequestBody UserRequestDTO request, @PathVariable Long id) {
		logger.info(">>> Initializing the controller's update method.");
		
		UserResponseDTO response = userService.update(request, id);
		
		logger.info(">>> Finishing the controller's update method.");
		
		return response;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's delete method.");
		
		userService.delete(id);
		
		logger.info(">>> Finishing the controller's delete method.");
		return ResponseEntity.noContent().build();
	}

}
