package com.lucasdevx.Mentorly.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.model.Role;
import com.lucasdevx.Mentorly.model.User;
import com.lucasdevx.Mentorly.model.enums.RoleEnum;
import com.lucasdevx.Mentorly.repository.RoleRepository;
import com.lucasdevx.Mentorly.service.UserService;

@Component
public class UserMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserMapper(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User converterToEntity(UserRequestDTO request) {
		logger.info(">>> Converting User DTO to Entity.");
		
		User user = new User();
		
		logger.debug(">>> Setting firstName.");
		user.setFirstName(request.firstName());
		
		logger.debug(">>> Setting lastName.");
		user.setLastName(request.lastName());
		
		logger.debug(">>> Setting email.");
		user.setEmail(request.email());
		
		logger.debug(">>> Setting password.");
		user.setPassword(passwordEncoder.encode(request.password()));
		
		logger.debug(">>> Searching for Role entity in database.");
		Role role = roleRepository.findByRole(RoleEnum.valueOf(request.role().toUpperCase()));
		
		logger.debug(">>> Setting role.");
		user.getRoles().add(role);
		
		logger.info(">>> The User DTO conversion was successful.");
		return user;
	}
	
	public UserResponseDTO converterToDto(User user) {
		logger.info(">>> Converting User Entity to DTO.");
		String role = user.getRoles().stream().findFirst().get().getRole().name();
		
		UserResponseDTO response = new UserResponseDTO(
												user.getId(),
												user.getFirstName(),
												user.getLastName(),
												user.getEmail(),
												role,
												user.isActive(),
												user.getCreatedAt(),
												user.getUpdatedAt());
		
		logger.info(">>> The User Entity conversion was successful.");
		
		return response;
	}
}
