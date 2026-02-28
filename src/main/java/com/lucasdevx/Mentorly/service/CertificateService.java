package com.lucasdevx.Mentorly.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.dto.response.CertificateResponseDTO;
import com.lucasdevx.Mentorly.mapper.CertificateMapper;
import com.lucasdevx.Mentorly.model.Certificate;
import com.lucasdevx.Mentorly.repository.CertificateRepository;

@Service
public class CertificateService {

	private CertificateRepository certificateRepository;
	private CertificateMapper certificateMapper;
	
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
		Certificate certificatePersisted = certificateRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		
		CertificateResponseDTO response = certificateMapper.converterToDto(certificatePersisted);
		
		return response;
	}
	
	public List<CertificateResponseDTO> findAll() {
		List<Certificate> certificatesPersisted = certificateRepository.findAll();
		
		List<CertificateResponseDTO> responsesDTO = certificatesPersisted.stream()
				.map((response) -> certificateMapper.converterToDto(response))
				.toList();
		
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
		/*Certificate certificatePersisted = certificateRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		*/
		certificateRepository.deleteById(id);
	}
	
}
