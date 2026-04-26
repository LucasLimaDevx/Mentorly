package com.lucasdevx.Mentorly.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.controller.CertificateController;
import com.lucasdevx.Mentorly.dto.response.CertificateResponseDTO;
import com.lucasdevx.Mentorly.model.Certificate;
import com.lucasdevx.Mentorly.service.UserService;

@Component
public class CertificateMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private CourseMapper courseMapper;
	private UserMapper userMapper;
	
	public CertificateMapper(CourseMapper courseMapper, UserMapper userMapper) {
		this.courseMapper = courseMapper;
		this.userMapper = userMapper;
	}
	
	public CertificateResponseDTO converterToDto(Certificate certificate) {
		logger.info(">>> Converting Certificate Entity to DTO.");
		
		CertificateResponseDTO response = new CertificateResponseDTO(
										  certificate.getId(),
										  certificate.getIssueDate(),
										  courseMapper.converterToDto(certificate.getCourse()),
										  userMapper.converterToDto(certificate.getUser()));
		
		logger.info(">>> The Certificate Entity conversion was successful.");
		
		return response;
	}
	
	public EntityModel<CertificateResponseDTO> addHateoasLinks(CertificateResponseDTO certificateDTO, String role) {
		Long id = certificateDTO.id();
		logger.info(">>> Adding links HATEOAS.");
		
		if(role == null) {
			EntityModel<CertificateResponseDTO> model =  EntityModel.of(certificateDTO,
					linkTo(methodOn(CertificateController.class).findById(id)).withSelfRel().withType("GET"),
					linkTo(methodOn(CertificateController.class).findAll()).withRel("findAll").withType("GET"),
					linkTo(methodOn(CertificateController.class).delete(id)).withRel("delete").withType("DELETE"));
			
			logger.info(">>> The HATEOAS links have been successfully added.");
			
			return model;
		}
		
		EntityModel<CertificateResponseDTO> model =  EntityModel.of(certificateDTO,
				linkTo(methodOn(CertificateController.class).findAllCertificatesAuthStudent(null)).withRel("findAllCertificatesAuthStudent").withType("GET"));
		
		logger.info(">>> The HATEOAS links have been successfully added.");
		
		return model;
		
		
	}
}
