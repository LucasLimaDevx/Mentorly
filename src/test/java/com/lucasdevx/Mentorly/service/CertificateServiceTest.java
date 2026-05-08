package com.lucasdevx.Mentorly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lucasdevx.Mentorly.dto.response.CertificateResponseDTO;
import com.lucasdevx.Mentorly.mapper.CertificateMapper;
import com.lucasdevx.Mentorly.mocks.MockCertificate;
import com.lucasdevx.Mentorly.model.Certificate;
import com.lucasdevx.Mentorly.repository.CertificateRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {
	
	@Mock
	CertificateRepository certificateRepository;
	
	@Mock
	CertificateMapper certificateMapper;
	
	CertificateService certificateService;
	MockCertificate input;
	SimpleDateFormat formatter;
	@BeforeEach
	void setUp() throws Exception {
		
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		input = new MockCertificate();
		certificateService = new CertificateService(certificateRepository, certificateMapper);
	}

	@Test
	void findById() throws ParseException {
		Certificate certificatePersisted = input.mockEntity(1);
		CertificateResponseDTO response = input.mockResponseDTO(1);
		
		when(certificateRepository.findById(anyLong())).thenReturn(Optional.of(certificatePersisted));
		when(certificateMapper.converterToDto(any(Certificate.class))).thenReturn(response);
		
		var result = certificateService.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getContent().id());
		assertNotNull(result.getContent().student());
		assertNotNull(result.getContent().course());
		assertEquals(1L, result.getContent().id());
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().issueDate()));
		
	}

	@Test
	void findAll() {
		
	}

	@Test
	void delete() {
		
	}

}
