package com.lucasdevx.Mentorly.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.dto.request.EnrollmentRequestDTO;
import com.lucasdevx.Mentorly.dto.response.EnrollmentResponseDTO;
import com.lucasdevx.Mentorly.mapper.EnrollmentMapper;
import com.lucasdevx.Mentorly.model.Enrollment;
import com.lucasdevx.Mentorly.repository.EnrollmentRepository;

@Service
public class EnrollmentService {

	private EnrollmentRepository enrollmentRepository;
	private EnrollmentMapper enrollmentMapper;
	
	public EnrollmentService(EnrollmentRepository enrollmentRepository, EnrollmentMapper enrollmentMapper) {
		this.enrollmentRepository = enrollmentRepository;
		this.enrollmentMapper = enrollmentMapper;
	}
	
	public EnrollmentResponseDTO create(EnrollmentRequestDTO request) {
		
		Enrollment enrollment = enrollmentMapper.converterToEntity(request);
		
		enrollment.setEnrollmentDate(new Date());
		Enrollment enrollmentPersisted = enrollmentRepository.save(enrollment);

		EnrollmentResponseDTO response = enrollmentMapper.converterToDto(enrollmentPersisted);
		
		return response;
	}
	
	public EnrollmentResponseDTO findById(Long id) {
		Enrollment enrollmentPersisted = enrollmentRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		
		EnrollmentResponseDTO response = enrollmentMapper.converterToDto(enrollmentPersisted);
		
		return response;
	}
	
	public List<EnrollmentResponseDTO> findAll() {
		List<Enrollment> enrollmentsPersisted = enrollmentRepository.findAll();
		
		List<EnrollmentResponseDTO> responsesDTO = enrollmentsPersisted.stream()
				.map((response) -> enrollmentMapper.converterToDto(response))
				.toList();
		
		return responsesDTO;
	}
	
	public EnrollmentResponseDTO update(EnrollmentRequestDTO request ,Long id) {
		Enrollment enrollmentPersisted = enrollmentRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Invalid ID"));
		
		Enrollment enrollmentUpdated = updateDate(enrollmentPersisted, request);
		
		EnrollmentResponseDTO response = enrollmentMapper.converterToDto(enrollmentRepository.save(enrollmentUpdated));
		
		return response;
	}
	
	public void delete(Long id) {
		/*Enrollment enrollmentPersisted = enrollmentRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		*/
		enrollmentRepository.deleteById(id);
	}
	
	public Enrollment updateDate(Enrollment enrollment, EnrollmentRequestDTO request) {
		
		enrollment.setProgressPercentage(request.getProgressPercentage());
		
		return enrollment;
	}
}
