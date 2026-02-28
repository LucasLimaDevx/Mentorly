package com.lucasdevx.Mentorly.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.CourseRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
import com.lucasdevx.Mentorly.model.Course;
import com.lucasdevx.Mentorly.service.UserService;

@Component
public class CourseMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public Course converterToEntity(CourseRequestDTO request) {
		logger.info(">>> Converting DTO to Entity.");
		Course course = new Course();
		
		logger.debug(">>> Setting title.");
		course.setTitle(request.getTitle());
		
		logger.debug(">>> Setting workloadHours.");
		course.setWorkloadHours(request.getWorkloadHours());
	
		logger.info(">>> The DTO conversion was successful.");
		
		return course;
	}
	
	public CourseResponseDTO converterToDto(Course course) {
		logger.info(">>> Converting Entity to DTO.");
		
		CourseResponseDTO response = new CourseResponseDTO(course);
		
		logger.info(">>> The Entity conversion was successful.");
		return response;
		
	}
}
