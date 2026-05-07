package com.lucasdevx.Mentorly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.lucasdevx.Mentorly.dto.request.CourseRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
import com.lucasdevx.Mentorly.mapper.CourseMapper;
import com.lucasdevx.Mentorly.mocks.MockCourse;
import com.lucasdevx.Mentorly.model.Category;
import com.lucasdevx.Mentorly.model.Course;
import com.lucasdevx.Mentorly.repository.CategoryRepository;
import com.lucasdevx.Mentorly.repository.CourseRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
	
	@Mock
	private CourseRepository courseRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private CourseMapper courseMapper;
	
	MockCourse input;
	CourseService courseService;
	SimpleDateFormat formatter;
	
	@BeforeEach
	void setUp() throws Exception {
		courseService = new CourseService(courseRepository, categoryRepository, courseMapper);
		input = new MockCourse();
	}


	@Test
	void create() throws ParseException {
		CourseRequestDTO request = input.mockRequestDTO(1);
		Course course = input.mockEntity(1);
		Course coursePersisted = course;
		CourseResponseDTO response = input.mockResponseDTO(1);
		Category category = course.getCategory();
		
		when(courseMapper.converterToEntity(any(CourseRequestDTO.class))).thenReturn(course);
		when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
		when(courseRepository.save(any(Course.class))).thenReturn(coursePersisted);
		when(courseMapper.converterToDto(any(Course.class))).thenReturn(response);
		
		var result = courseService.create(request);
		
		assertNotNull(result);
		assertNotNull(result.getContent().category());
		assertEquals(1L, result.getContent().id());
		assertEquals("Title Test1", result.getContent().title());
		assertEquals(30, result.getContent().workloadHours());
		assertEquals(false, result.getContent().active());
		assertTrue("BEGINNER".equals(result.getContent().courseLevel()) ||
				   "INTERMEDIATE".equals(result.getContent().courseLevel()) ||
				   "ADVANCED".equals(result.getContent().courseLevel()));
		
	}

	@Test
	void findByIdWithUserRole() throws ParseException {
		Course coursePersisted = input.mockEntity(1);
		CourseResponseDTO courseDTO = input.mockResponseDTO(1);
		
		when(courseRepository.findById(anyLong())).thenReturn(Optional.of(coursePersisted));
		when(courseMapper.converterToDto(any(Course.class))).thenReturn(courseDTO);
		
		var result = courseService.findById(coursePersisted.getId(), "USER");
		
		assertNotNull(result);
		assertNotNull(result.getContent().category());
		assertEquals(1L, result.getContent().id());
		assertEquals("Title Test1", result.getContent().title());
		assertEquals(30, result.getContent().workloadHours());
		assertEquals(false, result.getContent().active());
		assertTrue("BEGINNER".equals(result.getContent().courseLevel()) ||
				   "INTERMEDIATE".equals(result.getContent().courseLevel()) ||
				   "ADVANCED".equals(result.getContent().courseLevel()));

	}
	
	@Test
	void findByIdWithUserAdmin() throws ParseException {
		Course coursePersisted = input.mockEntity(1);
		CourseResponseDTO courseDTO = input.mockResponseDTO(1);
		
		when(courseRepository.findById(anyLong())).thenReturn(Optional.of(coursePersisted));
		when(courseMapper.converterToDto(any(Course.class))).thenReturn(courseDTO);
		
		var result = courseService.findById(coursePersisted.getId(), "ADMIN");
		
		assertNotNull(result);
		assertNotNull(result.getContent().category());
		assertEquals(1L, result.getContent().id());
		assertEquals("Title Test1", result.getContent().title());
		assertEquals(30, result.getContent().workloadHours());
		assertEquals(false, result.getContent().active());
		assertTrue("BEGINNER".equals(result.getContent().courseLevel()) ||
				   "INTERMEDIATE".equals(result.getContent().courseLevel()) ||
				   "ADVANCED".equals(result.getContent().courseLevel()));

	}
	
	@Test
	void testFindAll() {
	}

	@Test
	void testUpdate() {
		
	}

	@Test
	void testDelete() {
		
	}

	@Test
	void testUpdateData() {
		
	}

}
