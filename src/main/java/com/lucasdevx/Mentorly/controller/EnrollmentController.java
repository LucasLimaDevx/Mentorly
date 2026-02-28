package com.lucasdevx.Mentorly.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdevx.Mentorly.dto.request.EnrollmentRequestDTO;
import com.lucasdevx.Mentorly.dto.response.EnrollmentResponseDTO;
import com.lucasdevx.Mentorly.service.EnrollmentService;

@RestController
@RequestMapping("/enrollments/v1")
public class EnrollmentController {
	
	private EnrollmentService enrollmentService;

	public EnrollmentController(EnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}
	
	@PostMapping
	public EnrollmentResponseDTO create(@RequestBody EnrollmentRequestDTO request) {
		EnrollmentResponseDTO response = enrollmentService.create(request);
		return response;
	}
	
	@GetMapping("/{id}")
	public EnrollmentResponseDTO findById(@PathVariable Long id) {
		EnrollmentResponseDTO response = enrollmentService.findById(id);
		return response;
	}
	
	@GetMapping
	public List<EnrollmentResponseDTO> findAll() {
		List<EnrollmentResponseDTO> responsesDTO = enrollmentService.findAll();
		return responsesDTO;
	}
	
	@PutMapping("/{id}")
	public EnrollmentResponseDTO update(@RequestBody EnrollmentRequestDTO request, @PathVariable Long id) {
		EnrollmentResponseDTO response = enrollmentService.update(request, id);
		return response;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		enrollmentService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
