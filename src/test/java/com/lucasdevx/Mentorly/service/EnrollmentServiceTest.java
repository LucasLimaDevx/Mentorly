package com.lucasdevx.Mentorly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
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

import com.lucasdevx.Mentorly.dto.request.EnrollmentRequestDTO;
import com.lucasdevx.Mentorly.dto.response.EnrollmentResponseDTO;
import com.lucasdevx.Mentorly.mapper.EnrollmentMapper;
import com.lucasdevx.Mentorly.mocks.MockEnrollment;
import com.lucasdevx.Mentorly.model.Enrollment;
import com.lucasdevx.Mentorly.repository.CertificateRepository;
import com.lucasdevx.Mentorly.repository.CourseRepository;
import com.lucasdevx.Mentorly.repository.EnrollmentRepository;
import com.lucasdevx.Mentorly.repository.UserRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

	@Mock
	EnrollmentRepository enrollmentRepository;
	
	@Mock
	CourseRepository courseRepository;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	CertificateRepository certificateRepository;
	
	@Mock
	EnrollmentMapper enrollmentMapper;
	
	MockEnrollment input;
	EnrollmentService enrollmentService;
	SimpleDateFormat formatter;
	
	@BeforeEach
	void setUp() throws Exception {
		
		input = new MockEnrollment();
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		enrollmentService = spy(new EnrollmentService(enrollmentRepository, certificateRepository, userRepository, courseRepository, enrollmentMapper));
		
	}


	@Test
	void create() throws ParseException {
		EnrollmentRequestDTO request = input.mockRequestDTO(1);
		Enrollment enrollment = input.mockEntity(1);
		Enrollment enrollmentPersisted = enrollment;
		EnrollmentResponseDTO response = input.mockResponseDTO(1);
		
		when(enrollmentMapper.converterToEntity(any(EnrollmentRequestDTO.class))).thenReturn(enrollment);
		when(courseRepository.findById(anyLong())).thenReturn(Optional.of(enrollment.getCourse()));
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(enrollment.getUser()));
		when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollmentPersisted);
		when(enrollmentMapper.converterToDto(any(Enrollment.class))).thenReturn(response);
		
		var result = enrollmentService.create(request, 1L, "USER");
	
		assertNotNull(result.getLinks());
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> 
					link.getRel().value().equals("self") &&
					link.getHref().endsWith("/users/v1/me") &&
					link.getType().equals("GET")
		));
		
		
		assertNotNull(result.getLinks());
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> 
					link.getRel().value().equals("updateAuthStudent") &&
					link.getHref().endsWith("/users/v1/me") &&
					link.getType().equals("UPDATE")
		));
		
		assertNotNull(result.getLinks());
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> 
					link.getRel().value().equals("deleteAuthStudent") &&
					link.getHref().endsWith("/users/v1/me") &&
					link.getType().equals("DELETE")
		));
		
		assertNotNull(result);
		assertNotNull(result.getContent().course());
		assertEquals(1L, result.getContent().id());
		assertEquals(300, result.getContent().progressPercentage());
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().enrollmentDate()));
		
		
	}
	
	
	@Test
	void findById() throws ParseException {
		Enrollment enrollmentPersisted = input.mockEntity(1);
		EnrollmentResponseDTO enrollmentDTO = input.mockResponseDTO(1);
		
		when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.of(enrollmentPersisted));
		when(enrollmentMapper.converterToDto(any(Enrollment.class))).thenReturn(enrollmentDTO);
		
		var result = enrollmentService.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getContent().course());
		assertEquals(1L, result.getContent().id());
		assertEquals(300, result.getContent().progressPercentage());
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().enrollmentDate()));
	}

	@Test
	void findAllWithUserRole() throws ParseException {
		List<Enrollment> enrollments = 
				List.of(input.mockEntity(1), 
						input.mockEntity(2), 
						input.mockEntity(3), 
						input.mockEntity(1), 
						input.mockEntity(2),
						input.mockEntity(1),
						input.mockEntity(2),
						input.mockEntity(3));
		
		List<EnrollmentResponseDTO> enrollmentsDTO = 
				 List.of(input.mockResponseDTO(1), 
						 input.mockResponseDTO(1),
					     input.mockResponseDTO(1));
		
		AtomicInteger index = new AtomicInteger();
		
		when(enrollmentRepository.findAll()).thenReturn(enrollments);
		when(enrollmentMapper.converterToDto(any(Enrollment.class)))
			.thenAnswer(invocation -> enrollmentsDTO.get(index.getAndIncrement()));
		
		var result = enrollmentService.findAll(1L, "USER");
		
		assertNotNull(result);
		assertEquals(3, result.size());
		
		EnrollmentResponseDTO enrollmentOne = result.get(0).getContent();
		assertNotNull(enrollmentOne.course());
		assertEquals(1, enrollmentOne.id());
		assertEquals(300, enrollmentOne.progressPercentage());
		assertEquals("12/07/2026 10:00", formatter.format(enrollmentOne.enrollmentDate()));
				
		EnrollmentResponseDTO enrollmentTwo = result.get(1).getContent();
		assertNotNull(enrollmentTwo.course());
		assertEquals(1L, enrollmentTwo.id());
		assertEquals(300, enrollmentTwo.progressPercentage());
		assertEquals("12/07/2026 10:00", formatter.format(enrollmentTwo.enrollmentDate()));
	}
	
	@Test
	void findAllWithAdminRole() throws ParseException {
		List<Enrollment> enrollments = input.mockEntityList();
		List<EnrollmentResponseDTO> enrollmentsDTO = input.mockResponseDTOList();
		
		AtomicInteger index = new AtomicInteger();
		
		when(enrollmentRepository.findAll()).thenReturn(enrollments);
		when(enrollmentMapper.converterToDto(any(Enrollment.class)))
			.thenAnswer(invocation -> enrollmentsDTO.get(index.getAndIncrement()));
		
		var result = enrollmentService.findAll(1L, null);
		
		
		assertNotNull(result);
		assertEquals(14, result.size());
		
		EnrollmentResponseDTO enrollmentOne = result.get(1).getContent();
		
		assertNotNull(enrollmentOne.course());
		assertEquals(1L, enrollmentOne.id());
		assertEquals(300, enrollmentOne.progressPercentage());
		assertEquals("12/07/2026 10:00", formatter.format(enrollmentOne.enrollmentDate()));
		

		EnrollmentResponseDTO enrollmentEight = result.get(8).getContent();
		assertNotNull(enrollmentEight.course());
		assertEquals(8L, enrollmentEight.id());
		assertEquals(50, enrollmentEight.progressPercentage());
		assertEquals("12/07/2026 10:00", formatter.format(enrollmentEight.enrollmentDate()));
		

		EnrollmentResponseDTO enrollmentEleven = result.get(11).getContent();
		assertNotNull(enrollmentEleven.course());
		assertEquals(11L, enrollmentEleven.id());
		assertEquals(300, enrollmentEleven.progressPercentage());
		assertEquals("12/07/2026 10:00", formatter.format(enrollmentEleven.enrollmentDate()));
	}

	@Test
	void update() throws ParseException {
		EnrollmentRequestDTO request = input.mockRequestDTO(1);
		Enrollment enrollmentPersisted = input.mockEntity(1);
		EnrollmentResponseDTO enrollmentDTO = input.mockResponseDTO(1);
		
		when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.of(enrollmentPersisted));
		when(enrollmentMapper.converterToDto(enrollmentRepository.save(any(Enrollment.class)))).thenReturn(enrollmentDTO);
		doNothing().when(enrollmentService).addCertificate(any(Enrollment.class));
		
		var result = enrollmentService.update(request, 1L);
		
		assertNotNull(result);
		assertNotNull(result.getContent().course());
		assertEquals(1L, result.getContent().id());
		assertEquals(300, result.getContent().progressPercentage());
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().enrollmentDate()));

	}

	@Test
	void delete() {
		
		enrollmentService.delete(1L);
		
		verify(enrollmentRepository, times(1)).deleteById(anyLong());
		verifyNoMoreInteractions(enrollmentRepository);
	}

	

}
