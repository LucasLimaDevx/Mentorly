package com.lucasdevx.Mentorly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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
	void findAllWithUserRole() throws ParseException {
		List<Certificate> certificates = 
				List.of(input.mockEntity(1), 
						input.mockEntity(2), 
						input.mockEntity(3), 
						input.mockEntity(1), 
						input.mockEntity(2),
						input.mockEntity(1),
						input.mockEntity(2),
						input.mockEntity(3));
		
		List<CertificateResponseDTO> certificatesDTO = 
			    List.of(input.mockResponseDTO(1), 
						input.mockResponseDTO(1),
						input.mockResponseDTO(1));
		
		AtomicInteger index = new AtomicInteger();
		
		when(certificateRepository.findAll()).thenReturn(certificates);
		when(certificateMapper.converterToDto(any(Certificate.class)))
			.thenAnswer(invocation -> certificatesDTO.get(index.getAndIncrement()));
		
		var result = certificateService.findAll(1L, "USER");

		assertNotNull(result);
		assertEquals(3, result.size());
		
		CertificateResponseDTO certificateOne = result.get(0).getContent();
		
		assertNotNull(certificateOne);
		assertNotNull(certificateOne.id());
		assertNotNull(certificateOne.student());
		assertNotNull(certificateOne.course());
		assertEquals(1L, certificateOne.id());
		assertEquals("12/07/2026 10:00", formatter.format(certificateOne.issueDate()));
		
		
		CertificateResponseDTO certificateTwo = result.get(1).getContent();
		
		assertNotNull(certificateTwo);
		assertNotNull(certificateTwo.id());
		assertNotNull(certificateTwo.student());
		assertNotNull(certificateTwo.course());
		assertEquals(1L, certificateTwo.id());
		assertEquals("12/07/2026 10:00", formatter.format(certificateTwo.issueDate()));
	}
	
	@Test
	void findAllWithAdminRole() throws ParseException {
		List<Certificate> certificates = input.mockEntityList();
		List<CertificateResponseDTO> certificatesDTO = input.mockResponseDTOList();
		
		AtomicInteger index = new AtomicInteger();
		
		when(certificateRepository.findAll()).thenReturn(certificates);
		when(certificateMapper.converterToDto(any(Certificate.class)))
			.thenAnswer(invocation -> certificatesDTO.get(index.getAndIncrement()));
		
		var result = certificateService.findAll(1L, null);
		
		assertNotNull(result);
		assertEquals(14, result.size());
		
		CertificateResponseDTO certificateOne = result.get(1).getContent();
		
		assertNotNull(certificateOne);
		assertNotNull(certificateOne.id());
		assertNotNull(certificateOne.student());
		assertNotNull(certificateOne.course());
		assertEquals(1L, certificateOne.id());
		assertEquals("12/07/2026 10:00", formatter.format(certificateOne.issueDate()));
		
		CertificateResponseDTO certificateSix = result.get(6).getContent();
		
		assertNotNull(certificateSix);
		assertNotNull(certificateSix.id());
		assertNotNull(certificateSix.student());
		assertNotNull(certificateSix.course());
		assertEquals(6L, certificateSix.id());
		assertEquals("12/07/2026 10:00", formatter.format(certificateSix.issueDate()));
		
		CertificateResponseDTO certificateEleven = result.get(11).getContent();
		
		assertNotNull(certificateEleven);
		assertNotNull(certificateEleven.id());
		assertNotNull(certificateEleven.student());
		assertNotNull(certificateEleven.course());
		assertEquals(11L, certificateEleven.id());
		assertEquals("12/07/2026 10:00", formatter.format(certificateEleven.issueDate()));
	}

	@Test
	void delete() {
		certificateService.delete(1L);
		
		verify(certificateRepository, times(1)).deleteById(anyLong());
		verifyNoMoreInteractions(certificateRepository);
	}

}
