package com.lucasdevx.Mentorly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;

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
	void findById() {
	}

	@Test
	void findAll() {
	
	}

	@Test
	void update() {

	}

	@Test
	void delete() {
	}

}
