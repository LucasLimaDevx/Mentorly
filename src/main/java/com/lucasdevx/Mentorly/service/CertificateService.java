package com.lucasdevx.Mentorly.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.dto.response.CertificateResponseDTO;
import com.lucasdevx.Mentorly.mapper.CertificateMapper;
import com.lucasdevx.Mentorly.model.Certificate;
import com.lucasdevx.Mentorly.repository.CertificateRepository;

@Service
public class CertificateService {

	private CertificateRepository certificateRepository;
	private CertificateMapper certificateMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public CertificateService(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
		this.certificateRepository = certificateRepository;
		this.certificateMapper = certificateMapper;
	}
	/*
	public CertificateResponseDTO create(CertificateRequestDTO request) {
		
		Certificate certificate = certificateMapper.converterToEntity(request);
		
		Date dateNow = new Date();
		certificate.setCreatedAt(dateNow);
		certificate.setUpdatedAt(dateNow);
		
		certificate.setActive(true);
		
		Certificate certificatePersisted = certificateRepository.save(certificate);
		
		CertificateResponseDTO response = certificateMapper.converterToDto(certificatePersisted);
		
		return response;
	}
	*/

	public CertificateResponseDTO findById(Long id) {
		logger.info(">>> Initializing the service's findById method.");
		logger.info(">>> Searching for entity in database.");
		
		Certificate certificatePersisted = certificateRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		
		logger.info(">>> The entity was found.");
		
		CertificateResponseDTO response = certificateMapper.converterToDto(certificatePersisted);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public List<CertificateResponseDTO> findAll() {
		logger.info(">>> Initializing the service's findAll method.");
		logger.info(">>> Searching for entities in the database.");
		
		List<Certificate> certificatesPersisted = certificateRepository.findAll();
		
		logger.info(">>> The entities have been discovered.");
		
		List<CertificateResponseDTO> responsesDTO = certificatesPersisted.stream()
				.map((response) -> certificateMapper.converterToDto(response))
				.toList();
		
		logger.info(">>> Returning response.");
		return responsesDTO;
	}	
	
	/*
	public CertificateResponseDTO update(CertificateRequestDTO request ,Long id) {
		Certificate certificatePersisted = certificateRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Invalid ID"));
		
		if(request.getActive() != null) { 
			certificatePersisted.setActive(request.getActive());
		}
		
		Certificate certificateUpdated = updateDate(certificatePersisted, request);
		
		CertificateResponseDTO response = certificateMapper.converterToDto(certificateRepository.save(certificateUpdated));
		
		return response;
	}
	*/
	
	public void delete(Long id) {
		logger.info(">>> Initializing the service's delete method.");
		
		/*Certificate certificatePersisted = certificateRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		*/
		
		logger.info(">>> Deleting Entity by ID");
		certificateRepository.deleteById(id);
	}
	
}
