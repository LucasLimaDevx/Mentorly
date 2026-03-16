package com.lucasdevx.Mentorly.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public record UserResponseDTO(
	Long id,
	String firstName,
	String lastName,
	String email,
	String password,
	String role,
	Boolean active,
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	Date createdAt,
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	Date uptedAt
	) {}
