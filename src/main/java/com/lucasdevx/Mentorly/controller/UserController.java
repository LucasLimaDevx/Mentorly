package com.lucasdevx.Mentorly.controller;

import java.util.List;

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

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public UserResponseDTO create(@RequestBody UserRequestDTO request) {
		UserResponseDTO response = userService.create(request);
		return response;
	}
	
	@GetMapping("/{id}")
	public UserResponseDTO findById(@PathVariable Long id) {
		UserResponseDTO response = userService.findById(id);
		return response;
	}
	
	@GetMapping
	public List<UserResponseDTO> findAll() {
		List<UserResponseDTO> responsesDTO = userService.findAll();
		return responsesDTO;
	}
	
	@PutMapping("/{id}")
	public UserResponseDTO update(@RequestBody UserRequestDTO request, @PathVariable Long id) {
		UserResponseDTO response = userService.update(request, id);
		return response;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
