package com.lucasdevx.Mentorly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.text.ParseException;
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

import com.lucasdevx.Mentorly.dto.request.CategoryRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CategoryResponseDTO;
import com.lucasdevx.Mentorly.mapper.CategoryMapper;
import com.lucasdevx.Mentorly.mocks.MockCategory;
import com.lucasdevx.Mentorly.model.Category;
import com.lucasdevx.Mentorly.repository.CategoryRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
	
	@Mock
	CategoryRepository categoryRepository;
	
	@Mock
	CategoryMapper categoryMapper;
	
	CategoryService categoryService;
	MockCategory input;
	
	@BeforeEach
	void setUp() throws Exception {
		input = new MockCategory();
		categoryService = new CategoryService(categoryRepository, categoryMapper);
	}

	@Test
	void create() throws ParseException {
		CategoryRequestDTO request = input.mockRequestDTO(1);
		Category category = input.mockEntity(1);
		Category categoryPersisted = category;
		CategoryResponseDTO response = input.mockResponseDTO(1);

		when(categoryMapper.converterToEntity(any(CategoryRequestDTO.class))).thenReturn(category);
		when(categoryRepository.save(any(Category.class))).thenReturn(categoryPersisted);
		when(categoryMapper.converterToDto(any(Category.class))).thenReturn(response);
		
		var result = categoryService.create(request);
		
		assertNotNull(result);
		assertNotNull(result.getContent().id());
		assertEquals(1L, result.getContent().id());
		assertEquals("Title Test1", result.getContent().title());
		assertEquals("Description Test1", result.getContent().description());
	}

	@Test
	void findById() throws ParseException {
		Category categoryPersisted = input.mockEntity(1);
		CategoryResponseDTO response = input.mockResponseDTO(1);

		when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(categoryPersisted));
		when(categoryMapper.converterToDto(any(Category.class))).thenReturn(response);
		
		var result = categoryService.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getContent().id());
		assertEquals(1L, result.getContent().id());
		assertEquals("Title Test1", result.getContent().title());
		assertEquals("Description Test1", result.getContent().description());
	}

	@Test
	void findAll() throws ParseException {
		List<Category> categories = input.mockEntityList();
		List<CategoryResponseDTO> categoriesDTO = input.mockResponseDTOList();
		AtomicInteger index = new AtomicInteger();
		
		when(categoryRepository.findAll()).thenReturn(categories);
		when(categoryMapper.converterToDto(any(Category.class))).
			thenAnswer(invocation -> categoriesDTO.get(index.getAndIncrement()));
		
		var result = categoryService.findAll();
		
		assertNotNull(result);
		assertEquals(14, result.size());
		
		CategoryResponseDTO categoryOne = result.get(1).getContent();
		
		assertNotNull(categoryOne.id());
		assertEquals(1L, categoryOne.id());
		assertEquals("Title Test1", categoryOne.title());
		assertEquals("Description Test1", categoryOne.description());
		
		
		CategoryResponseDTO categoryEight = result.get(8).getContent();
		
		assertNotNull(categoryEight.id());
		assertEquals(8L, categoryEight.id());
		assertEquals("Title Test8", categoryEight.title());
		assertEquals("Description Test8", categoryEight.description());
		
		CategoryResponseDTO categoryEleven = result.get(11).getContent();
		
		assertNotNull(categoryEleven.id());
		assertEquals(11L, categoryEleven.id());
		assertEquals("Title Test11", categoryEleven.title());
		assertEquals("Description Test11", categoryEleven.description());
		
	
	}

	@Test
	void update() {

	}

	@Test
	void delete() {
	}

}
