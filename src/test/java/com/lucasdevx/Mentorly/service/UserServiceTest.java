package com.lucasdevx.Mentorly.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.mapper.UserMapper;
import com.lucasdevx.Mentorly.mocks.MockUser;
import com.lucasdevx.Mentorly.model.User;
import com.lucasdevx.Mentorly.repository.UserRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	UserService userService;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	UserMapper userMapper;
	
	MockUser input;
	
	@BeforeEach
	void setUp() throws Exception {
		
		input = new MockUser();
	}

	@Test
	void create() throws ParseException {
		UserRequestDTO request = input.mockRequestDTO(1);
		User user = input.mockEntity(1);
		User userPersisted = user;
		UserResponseDTO response = input.mockResponseDTO(1);
		
		when(userMapper.converterToEntity(request)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(userPersisted);
		when(userMapper.converterToDto(userPersisted)).thenReturn(response);
		
		var result = userService.create(request, "USER");
		
		assertNotNull(result);
	
		
	}

	@Test
	void findById() {
		fail("Not yet implemented");
	}

	@Test
	void findAll() {
		fail("Not yet implemented");
	}

	@Test
	void update() {
		fail("Not yet implemented");
	}

	@Test
	void delete() {
		fail("Not yet implemented");
	}

}
