package com.lucasdevx.Mentorly.service;

import java.util.Date;
import java.util.List;

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
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());
		user.setActive(true);
		
		User userPersisted = userRepository.save(user);
		UserResponseDTO response = new UserResponseDTO(userPersisted);
		
		return response;
	}
	
	public UserResponseDTO findById(Long id) {
		User userPersisted = userRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		
		UserResponseDTO response = new UserResponseDTO(userPersisted);
		
		return response;
	}
	
	public List<UserResponseDTO> findAll() {
		List<User> usersPersisted = userRepository.findAll();
		
		List<UserResponseDTO> responsesDTO = usersPersisted.stream()
				.map((response) -> new UserResponseDTO(response))
				.toList();
		
		return responsesDTO;
	}
	
	public UserResponseDTO update(UserRequestDTO request ,Long id) {
		User userPersisted = userRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Invalid ID"));
		
		userPersisted.setFullName(request.getFullName());
		userPersisted.setEmail(request.getEmail());
		userPersisted.setPassword(request.getPassword());
		userPersisted.setUpdatedAt(new Date());
		
		
		UserResponseDTO response = new UserResponseDTO(userRepository.save(userPersisted));
		
		return response;
	}
	
	public void delete(Long id) {
		/*User userPersisted = userRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		*/
		userRepository.deleteById(id);
	}
	
}
