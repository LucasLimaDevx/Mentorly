package com.lucasdevx.Mentorly.dto.request;

public record LessonRequestDTO (
	String title,
	String description,
	String videoUrl,
	int lessonOrder,
	Long courseId) {}
