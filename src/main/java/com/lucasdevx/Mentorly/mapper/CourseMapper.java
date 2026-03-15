package com.lucasdevx.Mentorly.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.CourseRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
import com.lucasdevx.Mentorly.model.Course;
import com.lucasdevx.Mentorly.model.enums.Level;
import com.lucasdevx.Mentorly.service.UserService;

@Component
public class CourseMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private CategoryMapper categoryMapper;
	
	public CourseMapper(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
	}
	
	public Course converterToEntity(CourseRequestDTO request) {
		logger.info(">>> Converting DTO to Entity.");
		Course course = new Course();
		
		logger.debug(">>> Setting title.");
		course.setTitle(request.title());
		
		logger.debug(">>> Setting workloadHours.");
		course.setWorkloadHours(request.workloadHours());
		
		logger.debug(">>> Setting courseLevel");
		course.setLevel(Level.valueOf(request.courseLevel()));
		
		logger.info(">>> The DTO conversion was successful.");
		
		return course;
	}
	
	public CourseResponseDTO converterToDto(Course course) {
		logger.info(">>> Converting Entity to DTO.");

		CourseResponseDTO response = new CourseResponseDTO(
									 course.getId(),
									 course.getTitle(),
									 course.getCreated(),
									 course.getWorkloadHours(),
									 course.isActive(),
									 course.getLevel().name(),
									 categoryMapper.converterToDto(course.getCategory()));
		
		logger.info(">>> The Entity conversion was successful.");
		return response;
		
	}
}
