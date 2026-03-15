package com.lucasdevx.Mentorly.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.EnrollmentRequestDTO;
import com.lucasdevx.Mentorly.dto.response.EnrollmentResponseDTO;
import com.lucasdevx.Mentorly.model.Enrollment;

@Component
public class EnrollmentMapper {
	private static final Logger logger = LoggerFactory.getLogger(EnrollmentMapper.class);
	private CourseMapper courseMapper;
	
	public EnrollmentMapper(CourseMapper courseMapper) {
		this.courseMapper = courseMapper;
	}
	public Enrollment converterToEntity(EnrollmentRequestDTO request) {
		logger.info(">>> Converting Enrollment DTO to Entity.");
		Enrollment enrollment = new Enrollment();
		
		logger.debug(">>> Setting progressPercentage.");
		enrollment.setProgressPercentage(request.progressPercentage());
		
		logger.info(">>> The  Enrollment DTO conversion was successful.");
		
		return enrollment;
	}
	
	public EnrollmentResponseDTO converterToDto(Enrollment enrollment) {
		logger.info(">>> Converting Enrollment Entity to DTO.");
		
		EnrollmentResponseDTO response = new EnrollmentResponseDTO(
										 enrollment.getId(),
										 enrollment.getEnrollmentDate(),
										 enrollment.getProgressPercentage(),
										 courseMapper.converterToDto(enrollment.getCourse()));
		
		logger.info(">>> The Enrollment Entity conversion was successful.");
		
		return response;
	}
}
