package com.lucasdevx.Mentorly.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdevx.Mentorly.dto.response.CertificateResponseDTO;
import com.lucasdevx.Mentorly.service.CertificateService;

@RestController
@RequestMapping("/certificates/v1")
public class CertificateController {
	
	private CertificateService certificateService;

	public CertificateController(CertificateService certificateService) {
		this.certificateService = certificateService;
	}
	
	
	@GetMapping("/{id}")
	public CertificateResponseDTO findById(@PathVariable Long id) {
		CertificateResponseDTO response = certificateService.findById(id);
		return response;
	}
	
	@GetMapping
	public List<CertificateResponseDTO> findAll() {
		List<CertificateResponseDTO> responsesDTO = certificateService.findAll();
		return responsesDTO;
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		certificateService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
