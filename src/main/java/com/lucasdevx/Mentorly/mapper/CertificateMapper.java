package com.lucasdevx.Mentorly.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.response.CertificateResponseDTO;
import com.lucasdevx.Mentorly.model.Certificate;
import com.lucasdevx.Mentorly.service.UserService;

@Component
public class CertificateMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public CertificateResponseDTO converterToDto(Certificate certificate) {
		logger.info(">>> Converting Entity to DTO.");
		
		CertificateResponseDTO response = new CertificateResponseDTO(certificate);
		
		logger.info(">>> The Entity conversion was successful.");
		
		return response;
	}
}
