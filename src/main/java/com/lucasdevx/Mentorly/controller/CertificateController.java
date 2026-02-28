package com.lucasdevx.Mentorly.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdevx.Mentorly.dto.response.CertificateResponseDTO;
import com.lucasdevx.Mentorly.service.CertificateService;
import com.lucasdevx.Mentorly.service.UserService;

@RestController
@RequestMapping("/certificates/v1")
public class CertificateController {
	
	private CertificateService certificateService;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public CertificateController(CertificateService certificateService) {
		this.certificateService = certificateService;
	}
	
	
	@GetMapping("/{id}")
	public CertificateResponseDTO findById(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's findById method.");
		
		CertificateResponseDTO response = certificateService.findById(id);
		
		logger.info(">>> Finishing the controller's findById method.");
		
		return response;
	}
	
	@GetMapping
	public List<CertificateResponseDTO> findAll() {
		logger.info(">>> Initializing the controller's findAll method.");
		
		List<CertificateResponseDTO> responsesDTO = certificateService.findAll();
		
		logger.info(">>> Finishing the controller's findAll method.");
		
		return responsesDTO;
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's delete method.");
		
		certificateService.delete(id);
		
		logger.info(">>> Finishing the controller's delete method.");
		
		return ResponseEntity.noContent().build();
	}

}
