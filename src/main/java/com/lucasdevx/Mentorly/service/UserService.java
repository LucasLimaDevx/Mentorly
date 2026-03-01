package com.lucasdevx.Mentorly.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.controller.UserController;
import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.mapper.UserMapper;
import com.lucasdevx.Mentorly.model.User;
import com.lucasdevx.Mentorly.repository.UserRepository;


@Service
public class UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}
	
	public EntityModel<UserResponseDTO> create(UserRequestDTO request) {
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
		EntityModel<UserResponseDTO> response = addHateoasLinks(userDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public EntityModel<UserResponseDTO> findById(Long id) {
		logger.info(">>> Initializing the service's findById method.");
		logger.info(">>> Searching for entity in database.");
		
		User userPersisted = userRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		
		logger.info(">>> The entity was found.");
		
		UserResponseDTO userDTO = userMapper.converterToDto(userPersisted);
		EntityModel<UserResponseDTO> response = addHateoasLinks(userDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public List<EntityModel<UserResponseDTO>> findAll() {
		logger.info(">>> Initializing the service's findAll method.");
		logger.info(">>> Searching for entities in the database.");
		
		List<User> usersPersisted = userRepository.findAll();
		
		logger.info(">>> The entities have been discovered.");
		
		List<UserResponseDTO> usersDTO = usersPersisted.stream()
				.map((response) -> userMapper.converterToDto(response))
				.toList();
		
		List<EntityModel<UserResponseDTO>> responsesDTO = usersDTO.stream()
				.map((userDTO) -> addHateoasLinks(userDTO))
				.toList();
		
		logger.info(">>> Returning response.");
		
		return responsesDTO;
	}
	
	public EntityModel<UserResponseDTO> update(UserRequestDTO request ,Long id) {
		logger.info(">>> Initializing the service's update method.");
		logger.info(">>> Searching for entity in database.");
		
		User userPersisted = userRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Invalid ID"));
		
		User userUpdated = updateData(userPersisted, request);
		
		UserResponseDTO userDTO = userMapper.converterToDto(userRepository.save(userUpdated));
		EntityModel<UserResponseDTO> response = addHateoasLinks(userDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public void delete(Long id) {
		logger.info(">>> Initializing the service's delete method.");
		/*User userPersisted = userRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		*/
		
		logger.info(">>> Deleting Entity by ID");
		userRepository.deleteById(id);
	}
	
	public User updateData(User user, UserRequestDTO request) {
		logger.info(">>> Updating the data.");
		
		logger.debug(">>> Updating first name.");
		user.setFirstName(request.getFirstName());
		
		logger.debug(">>> Updating last name.");
		user.setLastName(request.getLastName());
		
		logger.debug(">>> Updating email.");
		user.setEmail(request.getEmail());
		
		logger.debug(">>> Updating password.");
		user.setPassword(request.getPassword());
		
		logger.debug(">>> Updating date.");
		user.setUpdatedAt(new Date());
		
		logger.debug(">>> Checking if the active property is null.");
		if(request.getActive() != null) { 
			
			logger.debug(">>> Updating active.");
			user.setActive(request.getActive());
		}
		
		logger.info(">>> The data has been updated.");
		
		return user;
	}
	
	public EntityModel<UserResponseDTO> addHateoasLinks(UserResponseDTO userDTO) {
		Long id = userDTO.getId();
		logger.info(">>> Adding links HATEOAS.");
		EntityModel<UserResponseDTO> model =  EntityModel.of(userDTO,
				linkTo(methodOn(UserController.class).findById(id)).withSelfRel().withType("GET"),
				linkTo(methodOn(UserController.class).findAll()).withRel("findAll").withType("GET"),
				linkTo(methodOn(UserController.class).create(null)).withRel("create").withType("POST"),
				linkTo(methodOn(UserController.class).update(null, id)).withRel("update").withType("PUT"),
				linkTo(methodOn(UserController.class).delete(id)).withRel("delete").withType("DELETE"));
		
		logger.info(">>> The HATEOAS links have been successfully added.");
		
		return model;
		
	}
}
