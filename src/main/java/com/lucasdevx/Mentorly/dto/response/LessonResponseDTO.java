package com.lucasdevx.Mentorly.dto.response;

public record LessonResponseDTO(
	Long id,
	String title,
	String description,
	String videoUrl,
	int lessonOrder) {}
