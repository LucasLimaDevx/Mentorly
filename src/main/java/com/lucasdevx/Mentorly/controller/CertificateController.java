package com.lucasdevx.Mentorly.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdevx.Mentorly.config.JWTUserData;
import com.lucasdevx.Mentorly.config.TokenConfig;
import com.lucasdevx.Mentorly.controller.docs.CertificateControllerDocs;
import com.lucasdevx.Mentorly.dto.response.CertificateResponseDTO;
import com.lucasdevx.Mentorly.service.CertificateService;
import com.lucasdevx.Mentorly.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/certificates/v1")
@Tag(name = "Certificate", description = "Endpoints for managing Certificate.")
public class CertificateController  implements CertificateControllerDocs  {
	
	private CertificateService certificateService;
	private TokenConfig tokenConfig;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public CertificateController(CertificateService certificateService, TokenConfig tokenConfig) {
		this.certificateService = certificateService;
		this.tokenConfig = tokenConfig;
	}
	
	
	@GetMapping(
			value = "/{id}",
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				})
	public ResponseEntity<EntityModel<CertificateResponseDTO>> findById(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's findById method.");
		
		if(id <= 0) {
			throw new IllegalArgumentException("The ID provided is not valid.");
		}
		
		EntityModel<CertificateResponseDTO> response = certificateService.findById(id);
		
		logger.info(">>> Finishing the controller's findById method.");
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				})
	public ResponseEntity<List<EntityModel<CertificateResponseDTO>>> findAll() {
		logger.info(">>> Initializing the controller's findAll method.");
		
		List<EntityModel<CertificateResponseDTO>> responsesDTO = certificateService.findAll(null, null);
		
		logger.info(">>> Finishing the controller's findAll method.");
		
		return ResponseEntity.ok(responsesDTO);
	}
	
	@GetMapping(
			value = "/me",
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				})
	public ResponseEntity<List<EntityModel<CertificateResponseDTO>>> findAllCertificatesAuthStudent(HttpServletRequest httpRequest) {
		logger.info(">>> Initializing the controller's findAll method.");
		
		String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
		Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
		
		Long id = optUser.get().userId();
		String role = optUser.get().role();
		
		List<EntityModel<CertificateResponseDTO>> responsesDTO = certificateService.findAll(id, role);
		
		logger.info(">>> Finishing the controller's findAll method.");
		
		return ResponseEntity.ok(responsesDTO);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's delete method.");
		
		if(id <= 0) {
			throw new IllegalArgumentException("The ID provided is not valid.");
		}
		
		certificateService.delete(id);
		
		logger.info(">>> Finishing the controller's delete method.");
		
		return ResponseEntity.noContent().build();
	}

}
