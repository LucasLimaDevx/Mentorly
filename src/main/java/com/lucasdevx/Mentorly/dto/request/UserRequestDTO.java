package com.lucasdevx.Mentorly.dto.request;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String fullName;
	private String email;
	private String password;
	private Date createdAt;
	private Date updatedAt;
	private boolean active;

}
