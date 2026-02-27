package com.lucasdevx.Mentorly.dto.response;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date createdAt;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date updatedAt;
	private boolean active;
	
	public UserResponseDTO(User user) {
		this.id = user.getId();
		this.fullName = user.getFullName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.createdAt = user.getCreatedAt();
		this.updatedAt = user.getUpdatedAt();
		this.active = user.isActive();
		
	}
	
	
}
