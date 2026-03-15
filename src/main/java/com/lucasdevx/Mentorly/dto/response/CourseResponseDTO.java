package com.lucasdevx.Mentorly.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CourseResponseDTO (
	Long id,
	String title,
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	Date created,
	int workloadHours,
	Boolean active,
	String courseLevel,
	CategoryResponseDTO category) {}
