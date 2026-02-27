package com.lucasdevx.Mentorly.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.mapper.UserMapper;
import com.lucasdevx.Mentorly.model.User;
import com.lucasdevx.Mentorly.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;
	
	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}
	
	public UserResponseDTO create(UserRequestDTO request) {
		
		User user = userMapper.converterToEntity(request);
		
		Date dateNow = new Date();
		user.setCreatedAt(dateNow);
		user.setUpdatedAt(dateNow);
		
		user.setActive(true);
		
		User userPersisted = userRepository.save(user);
		
		UserResponseDTO response = userMapper.converterToDto(userPersisted);
		
		return response;
	}
	
	public UserResponseDTO findById(Long id) {
		User userPersisted = userRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		
		UserResponseDTO response = userMapper.converterToDto(userPersisted);
		
		return response;
	}
	
	public List<UserResponseDTO> findAll() {
		List<User> usersPersisted = userRepository.findAll();
		
		List<UserResponseDTO> responsesDTO = usersPersisted.stream()
				.map((response) -> userMapper.converterToDto(response))
				.toList();
		
		return responsesDTO;
	}
	
	public UserResponseDTO update(UserRequestDTO request ,Long id) {
		User userPersisted = userRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Invalid ID"));
		
		if(request.getActive() != null) { 
			userPersisted.setActive(request.getActive());
		}
		
		User userUpdated = updateDate(userPersisted, request);
		
		UserResponseDTO response = userMapper.converterToDto(userRepository.save(userUpdated));
		
		return response;
	}
	
	public void delete(Long id) {
		/*User userPersisted = userRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		*/
		userRepository.deleteById(id);
	}
	
	public User updateDate(User user, UserRequestDTO request) {
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setUpdatedAt(new Date());
		
		return user;
	}
}
