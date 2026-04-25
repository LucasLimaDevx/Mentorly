package com.lucasdevx.Mentorly.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.controller.EnrollmentController;
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
	
	public EntityModel<EnrollmentResponseDTO> addHateoasLinks(EnrollmentResponseDTO enrollmentDTO, String role) {
		Long id = enrollmentDTO.id();
		logger.info(">>> Adding links HATEOAS.");
		
		if(role == null) {
			EntityModel<EnrollmentResponseDTO> model =  EntityModel.of(enrollmentDTO,
					linkTo(methodOn(EnrollmentController.class).findById(id)).withSelfRel().withType("GET"),
					linkTo(methodOn(EnrollmentController.class).findAll()).withRel("findAll").withType("GET"),
					linkTo(methodOn(EnrollmentController.class).update(null, id)).withRel("update").withType("PUT"),
					linkTo(methodOn(EnrollmentController.class).delete(id)).withRel("delete").withType("DELETE"));
			
			logger.info(">>> The HATEOAS links have been successfully added.");
			
			return model;
		}
		
		EntityModel<EnrollmentResponseDTO> model =  EntityModel.of(enrollmentDTO,
				linkTo(methodOn(EnrollmentController.class).create(null, null)).withRel("create").withType("POST"),
				linkTo(methodOn(EnrollmentController.class).findAllEnrollmentAuthStudent(null)).withRel("findAllEnrollmentAuthStudent").withType("GET"));
		
		logger.info(">>> The HATEOAS links have been successfully added.");
		
		return model;
		
	}
}
