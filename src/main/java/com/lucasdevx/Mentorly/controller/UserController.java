package com.lucasdevx.Mentorly.controller;

import org.springframework.web.bind.annotation.PostMapping;
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
	
}
