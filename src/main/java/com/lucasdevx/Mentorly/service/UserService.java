package com.lucasdevx.Mentorly.service;

import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.model.User;
import com.lucasdevx.Mentorly.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserResponseDTO create(UserRequestDTO request) {
		User user = new User();
		
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setCreatedAt(request.getCreatedAt());
		user.setUpdatedAt(request.getUpdatedAt());
		user.setActive(request.isActive());
		
		return new UserResponseDTO(user);
	}
	
	
}
