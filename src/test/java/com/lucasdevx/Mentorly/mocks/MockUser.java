package com.lucasdevx.Mentorly.mocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.model.Role;
import com.lucasdevx.Mentorly.model.User;
import com.lucasdevx.Mentorly.model.enums.RoleEnum;

public class MockUser {
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public MockUser() {
	}
	
	public User MockEntity() throws ParseException {
		return mockEntity(0);
	}
	
	public UserRequestDTO mockRequestDTO() throws ParseException {
		return mockRequestDTO(0);
	}
	
	public UserResponseDTO MockResponseDTO() throws ParseException {
		return mockResponseDTO(0);
	}
	
	public List<User> mockEntityList() throws ParseException{
		List<User> list = new ArrayList<>();
		
		for(int i = 0 ; i < 14 ; i++) {
			list.add(mockEntity(i));
		}
		
		return list;
	}
	
	public List<UserResponseDTO> mockResponseDTOList() throws ParseException{
		List<UserResponseDTO> list = new ArrayList<>();
		
		for(int i = 0 ; i < 14 ; i++) {
			list.add(mockResponseDTO(i));
		}
		
		return list;
	}
	
	public User mockEntity(Integer number) throws ParseException {
		User user = new User();
		Role role1 = new Role();
		Role role2 = new Role();
		
		user.setId(number.longValue());
		user.setFirstName("First Name Test" + number);
		user.setLastName("Last Name Test" + number);
		user.setEmail("emailtest"+number+"@gmail.com");
		user.setPassword("password" + number);
		user.setActive((number % 2) == 0 ? true : false);
		user.setCreatedAt(formatter.parse("12/07/2026 10:00"));
		user.setUpdatedAt(formatter.parse("12/07/2026 10:00"));
		
		if(number % 2 == 0) {
			role1.setRole(RoleEnum.STUDENT);
			role2.setRole(RoleEnum.USER);
			
			user.getRoles().add(role1);
			user.getRoles().add(role2);
			
		}else {
			role1.setRole(RoleEnum.ADMIN);
			role2.setRole(RoleEnum.USER);
			
			user.getRoles().add(role1);
			user.getRoles().add(role2);
		}
		
		
		return user;
	}
	
	public UserResponseDTO mockResponseDTO(Integer number) throws ParseException {
		return new UserResponseDTO(
						number.longValue(),
						"First Name Test" + number,
						"Last Name Test" + number,
						"emailtest" + number + "@gmail.com",
						(number % 2) == 0 ? "USER" : "ADMIN",
						(number % 2) == 0 ? true : false,
						formatter.parse("12/07/2026 10:00"),
						formatter.parse("12/07/2026 10:00"));
	}
	
	public UserRequestDTO mockRequestDTO(Integer number) throws ParseException {
		return new UserRequestDTO(
						"First Name Test" + number,
						"Last Name Test" + number,
						"emailtest" + number + "@gmail.com",
						"password"+number,
						(number % 2) == 0 ? "USER" : "ADMIN",
						(number % 2) == 0 ? true : false);
	}
	
}
