package com.lucasdevx.Mentorly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().created()));
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
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().created()));
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
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().created()));
		assertTrue("BEGINNER".equals(result.getContent().courseLevel()) ||
				   "INTERMEDIATE".equals(result.getContent().courseLevel()) ||
				   "ADVANCED".equals(result.getContent().courseLevel()));

	}
	
	@Test
	void findAllWithUserRole() throws ParseException {
		List<Course> courses = input.mockEntityList();
		List<CourseResponseDTO> coursesDTO = input.mockResponseDTOList();
		
		AtomicInteger index = new AtomicInteger();
		
		when(courseRepository.findAll()).thenReturn(courses);
		when(courseMapper.converterToDto(any(Course.class)))
			.thenAnswer(invocation -> coursesDTO.get(index.getAndIncrement()));
		
		var result = courseService.findAll("USER");
		
		assertNotNull(result);
		
		CourseResponseDTO courseOne = result.get(1).getContent();
		
		assertNotNull(courseOne);
		assertNotNull(courseOne.category());
		assertEquals(1L, courseOne.id());
		assertEquals("Title Test1", courseOne.title());
		assertEquals(30, courseOne.workloadHours());
		assertEquals(false, courseOne.active());
		assertEquals("12/07/2026 10:00", formatter.format(courseOne.created()));
		assertTrue("BEGINNER".equals(courseOne.courseLevel()) ||
				   "INTERMEDIATE".equals(courseOne.courseLevel()) ||
				   "ADVANCED".equals(courseOne.courseLevel()));
		
		
		CourseResponseDTO courseEight = result.get(8).getContent();
		
		assertNotNull(courseEight);
		assertNotNull(courseEight.category());
		assertEquals(8L, courseEight.id());
		assertEquals("Title Test8", courseEight.title());
		assertEquals(30, courseEight.workloadHours());
		assertEquals(true, courseEight.active());
		assertEquals("12/07/2026 10:00", formatter.format(courseEight.created()));
		assertTrue("BEGINNER".equals(courseEight.courseLevel()) ||
				   "INTERMEDIATE".equals(courseEight.courseLevel()) ||
				   "ADVANCED".equals(courseEight.courseLevel()));
	}
	
	@Test
	void findAllWithAdminRole() throws ParseException {
		List<Course> courses = input.mockEntityList();
		List<CourseResponseDTO> coursesDTO = input.mockResponseDTOList();
		
		AtomicInteger index = new AtomicInteger();
		
		when(courseRepository.findAll()).thenReturn(courses);
		when(courseMapper.converterToDto(any(Course.class)))
			.thenAnswer(invocation -> coursesDTO.get(index.getAndIncrement()));
		
		var result = courseService.findAll("ADMIN");
		
		assertNotNull(result);
		
		CourseResponseDTO courseOne = result.get(1).getContent();
		
		assertNotNull(courseOne);
		assertNotNull(courseOne.category());
		assertEquals(1L, courseOne.id());
		assertEquals("Title Test1", courseOne.title());
		assertEquals(30, courseOne.workloadHours());
		assertEquals(false, courseOne.active());
		assertEquals("12/07/2026 10:00", formatter.format(courseOne.created()));
		assertTrue("BEGINNER".equals(courseOne.courseLevel()) ||
				   "INTERMEDIATE".equals(courseOne.courseLevel()) ||
				   "ADVANCED".equals(courseOne.courseLevel()));
		
		CourseResponseDTO courseEight = result.get(8).getContent();
		
		assertNotNull(courseEight);
		assertNotNull(courseEight.category());
		assertEquals(8L, courseEight.id());
		assertEquals("Title Test8", courseEight.title());
		assertEquals(30, courseEight.workloadHours());
		assertEquals(true, courseEight.active());
		assertEquals("12/07/2026 10:00", formatter.format(courseEight.created()));
		assertTrue("BEGINNER".equals(courseEight.courseLevel()) ||
				   "INTERMEDIATE".equals(courseEight.courseLevel()) ||
				   "ADVANCED".equals(courseEight.courseLevel()));
	}

	@Test
	void update() throws ParseException {
		CourseRequestDTO request = input.mockRequestDTO(1);
		Course coursePersisted = input.mockEntity(1);
		Course courseUpdated = coursePersisted;
		CourseResponseDTO response = input.mockResponseDTO(1);
		
		when(courseRepository.findById(anyLong())).thenReturn(Optional.of(coursePersisted));
		when(courseRepository.save(any(Course.class))).thenReturn(courseUpdated);
		when(courseMapper.converterToDto(any(Course.class))).thenReturn(response);
		
		var result = courseService.update(request, 1L);
		
		assertNotNull(result);
		assertNotNull(result.getContent().category());
		assertEquals(1L, result.getContent().id());
		assertEquals("Title Test1", result.getContent().title());
		assertEquals(30, result.getContent().workloadHours());
		assertEquals(false, result.getContent().active());
		assertEquals("12/07/2026 10:00", formatter.format(result.getContent().created()));
		assertTrue("BEGINNER".equals(result.getContent().courseLevel()) ||
				   "INTERMEDIATE".equals(result.getContent().courseLevel()) ||
				   "ADVANCED".equals(result.getContent().courseLevel()));
		
		
	}

	@Test
	void testDelete() {
		
		courseService.delete(1L);
		
		verify(courseRepository, times(1)).deleteById(anyLong());
		verifyNoMoreInteractions(courseRepository);
	}


}
