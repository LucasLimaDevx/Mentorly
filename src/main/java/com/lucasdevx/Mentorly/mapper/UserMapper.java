package com.lucasdevx.Mentorly.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.model.User;
import com.lucasdevx.Mentorly.service.UserService;

@Component
public class UserMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public User converterToEntity(UserRequestDTO request) {
		logger.info(">>> Converting DTO to Entity.");
		
		User user = new User();
		
		logger.debug(">>> Setting firstName.");
		user.setFirstName(request.firstName());
		
		logger.debug(">>> Setting lastName.");
		user.setLastName(request.lastName());
		
		logger.debug(">>> Setting email.");
		user.setEmail(request.email());
		
		logger.debug(">>> Setting password.");
		user.setPassword(request.password());
		
		logger.info(">>> The DTO conversion was successful.");
		return user;
	}
	
	public UserResponseDTO converterToDto(User user) {
		logger.info(">>> Converting Entity to DTO.");
		
		UserResponseDTO response = new UserResponseDTO(user);
		
		logger.info(">>> The Entity conversion was successful.");
		
		return response;
	}
}
