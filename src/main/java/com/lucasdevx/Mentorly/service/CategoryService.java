package com.lucasdevx.Mentorly.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}
	
	public CategoryResponseDTO create(CategoryRequestDTO request) {
		logger.info(">>> Initializing the service's create method.");
		
		Category category = categoryMapper.converterToEntity(request);
		
		logger.info(">>> Saving entity to database.");
		
		Category categoryPersisted = categoryRepository.save(category);
		
		logger.info(">>> The entity was saved in the database.");
		
		CategoryResponseDTO response = categoryMapper.converterToDto(categoryPersisted);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public CategoryResponseDTO findById(Long id) {
		logger.info(">>> Initializing the service's findById method.");
		logger.info(">>> Searching for entity in database.");
		
		Category categoryPersisted = categoryRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		
		logger.info(">>> The entity was found.");
		
		
		CategoryResponseDTO response = categoryMapper.converterToDto(categoryPersisted);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public List<CategoryResponseDTO> findAll() {
		logger.info(">>> Initializing the service's findAll method.");
		logger.info(">>> Searching for entities in the database.");
		
		List<Category> categorysPersisted = categoryRepository.findAll();
		
		logger.info(">>> The entities have been discovered.");
		
		List<CategoryResponseDTO> responsesDTO = categorysPersisted.stream()
				.map((response) -> categoryMapper.converterToDto(response))
				.toList();
		
		logger.info(">>> Returning response.");
		
		return responsesDTO;
	}
	
	public CategoryResponseDTO update(CategoryRequestDTO request ,Long id) {
		logger.info(">>> Initializing the service's update method.");
		logger.info(">>> Searching for entity in database.");
		
		Category categoryPersisted = categoryRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Invalid ID"));
		
		Category categoryUpdated = updateData(categoryPersisted, request);
		
		CategoryResponseDTO response = categoryMapper.converterToDto(categoryRepository.save(categoryUpdated));
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public void delete(Long id) {
		logger.info(">>> Initializing the service's delete method.");
		/*Category categoryPersisted = categoryRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		*/
		
		logger.info(">>> Deleting Entity by ID");
		categoryRepository.deleteById(id);
	}
	
	public Category updateData(Category category, CategoryRequestDTO request) {
		logger.info(">>> Updating the data.");
		
		logger.debug(">>> Updating title.");
		category.setTitle(request.getTitle());
		
		logger.debug(">>> Updating description.");
		category.setDescription(request.getDescription());
		
		logger.info(">>> The data has been updated.");
		
		return category;
	}
}
