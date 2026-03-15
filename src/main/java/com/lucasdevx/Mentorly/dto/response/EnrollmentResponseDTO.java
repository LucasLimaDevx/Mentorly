package com.lucasdevx.Mentorly.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public record EnrollmentResponseDTO(
	Long id,
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	Date enrollmentDate,
	int progressPercentage,
	CourseResponseDTO course) {}
