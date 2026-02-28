package com.lucasdevx.Mentorly.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.LessonRequestDTO;
import com.lucasdevx.Mentorly.dto.response.LessonResponseDTO;
import com.lucasdevx.Mentorly.model.Lesson;
import com.lucasdevx.Mentorly.service.UserService;

@Component
public class LessonMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public Lesson converterToEntity(LessonRequestDTO request) {
		logger.info(">>> Converting DTO to Entity.");
		Lesson lesson = new Lesson();
		
		logger.debug(">>> Setting title.");
		lesson.setTitle(request.getTitle());
		
		logger.debug(">>> Setting description.");
		lesson.setDescription(request.getDescription());
		
		logger.debug(">>> Setting videoUrl.");
		lesson.setVideoUrl(request.getVideoUrl());
		
		logger.debug(">>> Setting lessonOrder.");
		lesson.setLessonOrder(request.getLessonOrder());
		
		logger.info(">>> The DTO conversion was successful.");
		return lesson;
	}
	
	public LessonResponseDTO converterToDto(Lesson lesson) {
		logger.info(">>> Converting Entity to DTO.");
		
		LessonResponseDTO response = new LessonResponseDTO(lesson);
		
		logger.info(">>> The Entity conversion was successful.");
		
		return response;
	}
}
