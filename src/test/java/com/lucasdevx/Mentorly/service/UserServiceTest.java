package com.lucasdevx.Mentorly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.mapper.UserMapper;
import com.lucasdevx.Mentorly.mocks.MockUser;
import com.lucasdevx.Mentorly.model.User;
import com.lucasdevx.Mentorly.repository.RoleRepository;
import com.lucasdevx.Mentorly.repository.UserRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	RoleRepository roleRepository;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@Mock
	UserMapper userMapper;
	
	
	MockUser input;
	
	UserService userService;
	
	SimpleDateFormat formatter;
	
	
	@BeforeEach
	void setUp() throws Exception {
		
		userService = new UserService(userRepository, roleRepository, userMapper, passwordEncoder);
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		input = new MockUser();
	}

	@Test
	void createWithRoleUser() throws ParseException {
		UserRequestDTO request = input.mockRequestDTO(2);
		User user = input.mockEntity(2);
		User userPersisted = user;
		UserResponseDTO response = input.mockResponseDTO(2);
		
		when(userMapper.converterToEntity(request)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(userPersisted);
		when(userMapper.converterToDto(userPersisted)).thenReturn(response);
		var result = userService.create(request, request.role());
		
		assertNotNull(result);
		assertNotNull(result.getContent().id());
		assertEquals(2L, result.getContent().id());
		assertEquals("First Name Test2", result.getContent().firstName());
		assertEquals("Last Name Test2", result.getContent().lastName());
		assertEquals("emailtest2@gmail.com", result.getContent().email());
		assertEquals("USER", result.getContent().role());
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().createdAt()));
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().uptedAt()));
		
	}
	
	@Test
	void createWithRoleAdmin() throws ParseException {
		UserRequestDTO request = input.mockRequestDTO(1);
		User user = input.mockEntity(1);
		User userPersisted = user;
		UserResponseDTO response = input.mockResponseDTO(1);
		
		when(userMapper.converterToEntity(any(UserRequestDTO.class))).thenReturn(user);
		when(userRepository.save(any(User.class))).thenReturn(userPersisted);
		when(userMapper.converterToDto(any(User.class))).thenReturn(response);
		
		var result = userService.create(request, "admin");
		
		assertNotNull(result);
		assertNotNull(result.getContent().id());
		assertEquals(1L, result.getContent().id());
		assertEquals("First Name Test1", result.getContent().firstName());
		assertEquals("Last Name Test1", result.getContent().lastName());
		assertEquals("emailtest1@gmail.com", result.getContent().email());
		assertEquals("ADMIN", result.getContent().role());
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().createdAt()));
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().uptedAt()));
	
		
	}

	@Test
	void findByIdWithUserRole() throws ParseException {
		User userPersisted = input.mockEntity(2);
		UserResponseDTO response = input.mockResponseDTO(2);
		
		when(userRepository.findById(2L)).thenReturn(Optional.of(userPersisted));
		when(userMapper.converterToDto(userPersisted)).thenReturn(response);
		
		var result = userService.findById(2L);
		
		assertNotNull(result);
		assertNotNull(result.getContent().id());
		assertEquals(2L, result.getContent().id());
		assertEquals("First Name Test2", result.getContent().firstName());
		assertEquals("Last Name Test2", result.getContent().lastName());
		assertEquals("emailtest2@gmail.com", result.getContent().email());
		assertEquals("USER", result.getContent().role());
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().createdAt()));
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().uptedAt()));
		
		
	}
	
	@Test
	void findByIdWithAdminRole() throws ParseException {
		User userPersisted = input.mockEntity(1);
		UserResponseDTO response = input.mockResponseDTO(1);
		
		when(userRepository.findById(1L)).thenReturn(Optional.of(userPersisted));
		when(userMapper.converterToDto(userPersisted)).thenReturn(response);
		
		var result = userService.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getContent().id());
		assertEquals(1L, result.getContent().id());
		assertEquals("First Name Test1", result.getContent().firstName());
		assertEquals("Last Name Test1", result.getContent().lastName());
		assertEquals("emailtest1@gmail.com", result.getContent().email());
		assertEquals("ADMIN", result.getContent().role());
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().createdAt()));
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().uptedAt()));
	}

	@Test
	void findAll() throws ParseException {
		List<User> usersPersisted = input.mockEntityList();
		List<UserResponseDTO> usersDTO = input.mockResponseDTOList();
		
		AtomicInteger index = new AtomicInteger();
		
		when(userRepository.findAll()).thenReturn(usersPersisted);
		when(userMapper.converterToDto(any(User.class)))
			.thenAnswer(invocation -> usersDTO.get(index.getAndIncrement()));
		
		var result = userService.findAll();
		
		assertNotNull(result);
		assertEquals(14, result.size());

		UserResponseDTO userOne = result.get(1).getContent();
		
		System.out.println(result);
		assertNotNull(userOne);
		assertNotNull(userOne.id());
		assertEquals(1L, userOne.id());
		assertEquals("First Name Test1", userOne.firstName());
		assertEquals("Last Name Test1", userOne.lastName());
		assertEquals("emailtest1@gmail.com", userOne.email());
		assertEquals("ADMIN", userOne.role());
		assertEquals("12/07/2026 10:00", formatter.format(userOne.createdAt()));
		assertEquals("12/07/2026 10:00", formatter.format(userOne.uptedAt()));
		
		UserResponseDTO userTwo = result.get(2).getContent();
		
		assertNotNull(userTwo);
		assertNotNull(userTwo.id());
		assertEquals(2L, userTwo.id());
		assertEquals("First Name Test2", userTwo.firstName());
		assertEquals("Last Name Test2", userTwo.lastName());
		assertEquals("emailtest2@gmail.com", userTwo.email());
		assertEquals("USER", userTwo.role());
		assertEquals("12/07/2026 10:00", formatter.format(userTwo.createdAt()));
		assertEquals("12/07/2026 10:00", formatter.format(userTwo.uptedAt()));
		
	}

	@Test
	void updateWithUserRole() throws ParseException {
		UserRequestDTO request = input.mockRequestDTO(2);
		User userPersisted = input.mockEntity(2);
		UserResponseDTO response = input.mockResponseDTO(2);
		
		when(userRepository.findById(2L)).thenReturn(Optional.of(userPersisted));
		when(userMapper.converterToDto(userRepository.save(any(User.class)))).thenReturn(response);
		
		var result = userService.update(request, 2L, request.role());
		
		assertNotNull(result);
		assertNotNull(result.getContent().id());
		assertEquals(2L, result.getContent().id());
		assertEquals("First Name Test2", result.getContent().firstName());
		assertEquals("Last Name Test2", result.getContent().lastName());
		assertEquals("emailtest2@gmail.com", result.getContent().email());
		assertEquals("USER", result.getContent().role());
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().createdAt()));
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().uptedAt()));
		
		
	}
	
	@Test
	void updateWithAdminRole() throws ParseException {
		UserRequestDTO request = input.mockRequestDTO(1);
		User userPersisted = input.mockEntity(1);
		UserResponseDTO response = input.mockResponseDTO(1);
		
		when(userRepository.findById(1L)).thenReturn(Optional.of(userPersisted));
		when(userMapper.converterToDto(userRepository.save(any(User.class)))).thenReturn(response);
		
		var result = userService.update(request, 1L, request.role());
		
		assertNotNull(result);
		assertNotNull(result.getContent().id());
		assertEquals(1L, result.getContent().id());
		assertEquals("First Name Test1", result.getContent().firstName());
		assertEquals("Last Name Test1", result.getContent().lastName());
		assertEquals("emailtest1@gmail.com", result.getContent().email());
		assertEquals("ADMIN", result.getContent().role());
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().createdAt()));
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().uptedAt()));
		
		
	}

	@Test
	void delete() {
		
		userService.delete(1L);
		
		verify(userRepository, times(1)).deleteById(anyLong());
		verifyNoMoreInteractions(userRepository);
	}

}
