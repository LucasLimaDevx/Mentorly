package com.lucasdevx.Mentorly.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.dto.request.CategoryRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CategoryResponseDTO;
import com.lucasdevx.Mentorly.mapper.CategoryMapper;
import com.lucasdevx.Mentorly.model.Category;
import com.lucasdevx.Mentorly.repository.CategoryRepository;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository;
	private CategoryMapper categoryMapper;
	
	public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}
	
	public CategoryResponseDTO create(CategoryRequestDTO request) {
		
		Category category = categoryMapper.converterToEntity(request);
		
		Category categoryPersisted = categoryRepository.save(category);
		
		CategoryResponseDTO response = categoryMapper.converterToDto(categoryPersisted);
		
		return response;
	}
	
	public CategoryResponseDTO findById(Long id) {
		Category categoryPersisted = categoryRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		
		CategoryResponseDTO response = categoryMapper.converterToDto(categoryPersisted);
		
		return response;
	}
	
	public List<CategoryResponseDTO> findAll() {
		List<Category> categorysPersisted = categoryRepository.findAll();
		
		List<CategoryResponseDTO> responsesDTO = categorysPersisted.stream()
				.map((response) -> categoryMapper.converterToDto(response))
				.toList();
		
		return responsesDTO;
	}
	
	public CategoryResponseDTO update(CategoryRequestDTO request ,Long id) {
		Category categoryPersisted = categoryRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Invalid ID"));
		
		Category categoryUpdated = updateDate(categoryPersisted, request);
		
		CategoryResponseDTO response = categoryMapper.converterToDto(categoryRepository.save(categoryUpdated));
		
		return response;
	}
	
	public void delete(Long id) {
		/*Category categoryPersisted = categoryRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		*/
		categoryRepository.deleteById(id);
	}
	
	public Category updateDate(Category category, CategoryRequestDTO request) {
		
		category.setTitle(request.getTitle());
		category.setDescription(request.getDescription());
		
		return category;
	}
}
