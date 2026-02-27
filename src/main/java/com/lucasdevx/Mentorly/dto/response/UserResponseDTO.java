package com.lucasdevx.Mentorly.dto.response;

import java.io.Serializable;
import java.util.Date;

import com.lucasdevx.Mentorly.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String fullName;
	private String email;
	private String password;
	private Date createdAt;
	private Date updatedAt;
	private boolean active;
	
	public UserResponseDTO(User user) {
	
		this.fullName = user.getFullName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.createdAt = user.getCreatedAt();
		this.updatedAt = user.getUpdatedAt();
		this.active = user.isActive();
		
	}
	
	
}
