package com.lucasdevx.Mentorly.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.controller.UserController;
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
		
		if(role.getRole() == RoleEnum.USER) {
			Role roleStudent = roleRepository.findByRole(RoleEnum.STUDENT);
			user.getRoles().add(role);
			user.getRoles().add(roleStudent);
		}
		else {
			user.getRoles().add(role);
		}
		
		logger.info(">>> The User DTO conversion was successful.");
		return user;
	}
	
	public UserResponseDTO converterToDto(User user) {
		logger.info(">>> Converting User Entity to DTO.");
		String role = user.getRoles().stream().findFirst().get().getRole().name();
		
		if(role.equals("USER")){
			role = "STUDENT";
		}
		
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
	
	public static EntityModel<UserResponseDTO> addHateoasLinks(UserResponseDTO userDTO, String role) {
		Long id = userDTO.id();
		logger.info(">>> Adding links HATEOAS.");
		
		if(role.equals("ADMIN")) {
			EntityModel<UserResponseDTO> model =  EntityModel.of(userDTO,
					linkTo(methodOn(UserController.class).findById(id)).withSelfRel().withType("GET"),
					linkTo(methodOn(UserController.class).findAll()).withRel("findAll").withType("GET"),
					linkTo(methodOn(UserController.class).create(null)).withRel("create").withType("POST"),
					linkTo(methodOn(UserController.class).update(null, id)).withRel("update").withType("PUT"),
					linkTo(methodOn(UserController.class).delete(id)).withRel("delete").withType("DELETE"));
			
			logger.info(">>> The HATEOAS links have been successfully added.");
			
			return model;
		}
		
		EntityModel<UserResponseDTO> model =  EntityModel.of(userDTO,
				linkTo(methodOn(UserController.class).findAuthStudent(null)).withSelfRel().withType("GET"),
				linkTo(methodOn(UserController.class).updateAuthStudent(null, null)).withRel("updateAuthStudent").withType("PUT"),
				linkTo(methodOn(UserController.class).deleteAuthStudent(null)).withRel("deleteAuthStudent").withType("DELETE"));
		
		logger.info(">>> The HATEOAS links have been successfully added.");
		
		return model;
		
		
	}
}
