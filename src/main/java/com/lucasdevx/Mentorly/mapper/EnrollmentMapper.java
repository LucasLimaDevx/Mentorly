package com.lucasdevx.Mentorly.mapper;

import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.EnrollmentRequestDTO;
import com.lucasdevx.Mentorly.dto.response.EnrollmentResponseDTO;
import com.lucasdevx.Mentorly.model.Enrollment;

@Component
public class EnrollmentMapper {
	public Enrollment converterToEntity(EnrollmentRequestDTO request) {
		Enrollment enrollment = new Enrollment();
		
		enrollment.setProgressPercentage(request.getProgressPercentage());
		
		return enrollment;
	}
	
	public EnrollmentResponseDTO converterToDto(Enrollment enrollment) {
		return new EnrollmentResponseDTO(enrollment);
	}
}
