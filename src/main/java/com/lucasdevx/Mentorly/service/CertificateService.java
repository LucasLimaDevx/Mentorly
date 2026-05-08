package com.lucasdevx.Mentorly.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.dto.response.CertificateResponseDTO;
import com.lucasdevx.Mentorly.exception.ObjectNotFoundException;
import com.lucasdevx.Mentorly.mapper.CertificateMapper;
import com.lucasdevx.Mentorly.model.Certificate;
import com.lucasdevx.Mentorly.repository.CertificateRepository;

import jakarta.transaction.Transactional;

@Service
public class CertificateService {

	private CertificateRepository certificateRepository;
	private CertificateMapper certificateMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public CertificateService(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
		this.certificateRepository = certificateRepository;
		this.certificateMapper = certificateMapper;
	}
	
	@Transactional
	public EntityModel<CertificateResponseDTO> findById(Long id) {
		logger.info(">>> Initializing the service's findById method.");
		logger.info(">>> Searching for entity in database.");
		
		Certificate certificatePersisted = certificateRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object Certificate not found."));
		
		logger.info(">>> The entity was found.");
		
		CertificateResponseDTO certificateDTO = certificateMapper.converterToDto(certificatePersisted);
		EntityModel<CertificateResponseDTO> response = CertificateMapper.addHateoasLinks(certificateDTO, null);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	@Transactional
	public List<EntityModel<CertificateResponseDTO>> findAll(Long id, String role) {
		logger.info(">>> Initializing the service's findAll method.");
		logger.info(">>> Searching for entities in the database.");
		
		List<Certificate> certificatesPersisted = certificateRepository.findAll();
		
		logger.info(">>> The entities have been discovered.");
		
		if(role == null) {
			
			List<CertificateResponseDTO> certificatesDTO = certificatesPersisted.stream()
					.map((response) -> certificateMapper.converterToDto(response))
					.toList();
			
			List<EntityModel<CertificateResponseDTO>> responsesDTO = certificatesDTO.stream()
					.map((certificateDTO) -> CertificateMapper.addHateoasLinks(certificateDTO, null))
					.toList();
			
			logger.info(">>> Returning response.");
			return responsesDTO;
		}
		
		List<CertificateResponseDTO> certificatesDTO = certificatesPersisted.stream()
				.filter((certificate) -> certificate.getUser().getId().equals(id))
				.map((response) -> certificateMapper.converterToDto(response))
				.toList();
		
		List<EntityModel<CertificateResponseDTO>> responsesDTO = certificatesDTO.stream()
				.map((certificateDTO) -> CertificateMapper.addHateoasLinks(certificateDTO, role))
				.toList();
		
		logger.info(">>> Returning response.");
		return responsesDTO;
		
		
	}	
	
	public void delete(Long id) {
		logger.info(">>> Initializing the service's delete method.");
		
		/*Certificate certificatePersisted = certificateRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object Certificate not found."));
		*/
		
		logger.info(">>> Deleting Entity by ID");
		certificateRepository.deleteById(id);
	}	

}
