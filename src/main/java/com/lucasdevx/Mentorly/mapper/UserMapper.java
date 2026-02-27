package com.lucasdevx.Mentorly.mapper;

import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.model.User;

@Component
public class UserMapper {
	
	public User converterToEntity(UserRequestDTO request) {
		User user = new User();
		
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		
		return user;
	}
	
	public UserResponseDTO converterToDto(User user) {
		return new UserResponseDTO(user);
	}
}
