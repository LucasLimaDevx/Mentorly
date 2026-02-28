package com.lucasdevx.Mentorly.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.EnrollmentRequestDTO;
import com.lucasdevx.Mentorly.dto.response.EnrollmentResponseDTO;
import com.lucasdevx.Mentorly.model.Enrollment;
import com.lucasdevx.Mentorly.service.UserService;

@Component
public class EnrollmentMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public Enrollment converterToEntity(EnrollmentRequestDTO request) {
		logger.info(">>> Converting DTO to Entity.");
		Enrollment enrollment = new Enrollment();
		
		logger.debug(">>> Setting progressPercentage.");
		enrollment.setProgressPercentage(request.getProgressPercentage());
		
		logger.info(">>> The DTO conversion was successful.");
		
		return enrollment;
	}
	
	public EnrollmentResponseDTO converterToDto(Enrollment enrollment) {
		logger.info(">>> Converting Entity to DTO.");
		
		EnrollmentResponseDTO response = new EnrollmentResponseDTO(enrollment);
		
		logger.info(">>> The Entity conversion was successful.");
		
		return response;
	}
}
