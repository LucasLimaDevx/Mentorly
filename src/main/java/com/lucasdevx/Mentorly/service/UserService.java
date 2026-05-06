package com.lucasdevx.Mentorly.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.exception.ObjectNotFoundException;
import com.lucasdevx.Mentorly.mapper.UserMapper;
import com.lucasdevx.Mentorly.model.Role;
import com.lucasdevx.Mentorly.model.User;
import com.lucasdevx.Mentorly.model.enums.RoleEnum;
import com.lucasdevx.Mentorly.repository.RoleRepository;
import com.lucasdevx.Mentorly.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public EntityModel<UserResponseDTO> create(UserRequestDTO request, String role) {
		logger.info(">>> Initializing the service's create method.");
		
		User user = userMapper.converterToEntity(request);
		
		Date dateNow = new Date();
		
		logger.debug(">>> Setting createdAt.");
		user.setCreatedAt(dateNow);
		
		logger.debug(">>> Setting uptedAt.");
		user.setUpdatedAt(dateNow);
		
		logger.debug(">>>  Setting Active.");
		user.setActive(true);
		
		logger.info(">>> Saving entity to database.");
		User userPersisted = userRepository.save(user);
		
		
		UserResponseDTO userDTO = userMapper.converterToDto(userPersisted);
		EntityModel<UserResponseDTO> response = UserMapper.addHateoasLinks(userDTO, role.toUpperCase());
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	@Transactional
	public EntityModel<UserResponseDTO> findById(Long id) {
		logger.info(">>> Initializing the service's findById method.");
		logger.info(">>> Searching for entity in database.");
		
		User userPersisted = userRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object User not found."));
		
		logger.info(">>> The entity was found.");
		
		UserResponseDTO userDTO = userMapper.converterToDto(userPersisted);
		
		EntityModel<UserResponseDTO> response = UserMapper.addHateoasLinks(userDTO, userDTO.role());
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	@Transactional
	public List<EntityModel<UserResponseDTO>> findAll() {
		logger.info(">>> Initializing the service's findAll method.");
		logger.info(">>> Searching for entities in the database.");
		
		List<User> usersPersisted = userRepository.findAll();
		
		logger.info(">>> The entities have been discovered.");
		
		List<UserResponseDTO> usersDTO = usersPersisted.stream()
				.map((response) -> userMapper.converterToDto(response))
				.toList();
		
		List<EntityModel<UserResponseDTO>> responsesDTO = usersDTO.stream()
				.map((userDTO) -> UserMapper.addHateoasLinks(userDTO, userDTO.role()))
				.toList();
		
		logger.info(">>> Returning response.");
		
		return responsesDTO;
	}
	
	@Transactional
	public EntityModel<UserResponseDTO> update(UserRequestDTO request ,Long id, String role) {
		logger.info(">>> Initializing the service's update method.");
		logger.info(">>> Searching for entity in database.");
		
		User userPersisted = userRepository.findById(id).orElseThrow(
				()-> new ObjectNotFoundException("Object User not found."));
		
		User userUpdated = updateData(userPersisted, request, role);
		
		UserResponseDTO userDTO = userMapper.converterToDto(userRepository.save(userUpdated));
		EntityModel<UserResponseDTO> response = UserMapper.addHateoasLinks(userDTO, userDTO.role());
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public void delete(Long id) {
		logger.info(">>> Initializing the service's delete method.");
		/*
		User userPersisted = userRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object USER not found."));*/
	
		
		logger.info(">>> Deleting Entity by ID");
		userRepository.deleteById(id);
	}

	public User updateData(User user, UserRequestDTO request, String role) {
		logger.info(">>> Updating the data.");
		
		logger.debug(">>> Updating first name.");
		user.setFirstName(request.firstName());
		
		logger.debug(">>> Updating last name.");
		user.setLastName(request.lastName());
		
		logger.debug(">>> Updating email.");
		user.setEmail(request.email());
		
		logger.debug(">>> Updating password.");
		user.setPassword(passwordEncoder.encode(request.password()));
		
		logger.debug(">>> Updating date.");
		user.setUpdatedAt(new Date());
		
		logger.debug(">>> Checking if the active property is null.");
		if(request.active() != null) { 
			
			logger.debug(">>> Updating active.");
			user.setActive(request.active());
		}
		
		logger.debug(">>> Checking if the role's request property is equals role's entity.");
		String requestRole = request.role().toUpperCase();
		String currentRole = user.getRoles().stream().findFirst().get().getRole().name();
		
		if(role == null && !requestRole.equals(currentRole)) {

			logger.debug(">>> Searching for Role entity in database.");
			
			
			Role rolePersisted = roleRepository.findByRole(RoleEnum.valueOf(requestRole));
			if(rolePersisted.getRole() == RoleEnum.USER) {
				user.getRoles().clear();
				Role roleStudent = roleRepository.findByRole(RoleEnum.STUDENT);
				
				user.getRoles().add(rolePersisted);
				user.getRoles().add(roleStudent);
			}
			else {
				user.getRoles().clear();
				user.getRoles().add(rolePersisted);
			}
			
			logger.debug(">>> Updating role.");
		}
		
		logger.info(">>> The data has been updated.");
		return user;
	}
	
}
