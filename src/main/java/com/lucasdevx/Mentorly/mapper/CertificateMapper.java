package com.lucasdevx.Mentorly.mapper;

import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.response.CertificateResponseDTO;
import com.lucasdevx.Mentorly.model.Certificate;

@Component
public class CertificateMapper {
	
	public CertificateResponseDTO converterToDto(Certificate certificate) {
		return new CertificateResponseDTO(certificate);
	}
}
