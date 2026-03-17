package com.lucasdevx.Mentorly.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CertificateResponseDTO(
	Long id,
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	Date issueDate,
	CourseResponseDTO course,
	UserResponseDTO student) {}
