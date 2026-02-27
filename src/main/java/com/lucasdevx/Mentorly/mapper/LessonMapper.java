package com.lucasdevx.Mentorly.mapper;

import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.LessonRequestDTO;
import com.lucasdevx.Mentorly.dto.response.LessonResponseDTO;
import com.lucasdevx.Mentorly.model.Lesson;

@Component
public class LessonMapper {
	public Lesson converterToEntity(LessonRequestDTO request) {
		Lesson lesson = new Lesson();
		
		lesson.setTitle(request.getTitle());
		lesson.setDescription(request.getDescription());
		lesson.setVideoUrl(request.getVideoUrl());
		lesson.setLessonOrder(request.getLessonOrder());
		
		return lesson;
	}
	
	public LessonResponseDTO converterToDto(Lesson lesson) {
		return new LessonResponseDTO(lesson);
	}
}
