package com.lucasdevx.Mentorly.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.dto.request.CategoryRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CategoryResponseDTO;
import com.lucasdevx.Mentorly.exception.ObjectNotFoundException;
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
	
	public EntityModel<CategoryResponseDTO> create(CategoryRequestDTO request) {
		logger.info(">>> Initializing the service's create method.");
		
		Category category = categoryMapper.converterToEntity(request);
		
		logger.info(">>> Saving entity to database.");
		
		Category categoryPersisted = categoryRepository.save(category);
		
		logger.info(">>> The entity was saved in the database.");
		
		CategoryResponseDTO categoryDTO = categoryMapper.converterToDto(categoryPersisted);
		EntityModel<CategoryResponseDTO> response = CategoryMapper.addHateoasLinks(categoryDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public EntityModel<CategoryResponseDTO> findById(Long id) {
		logger.info(">>> Initializing the service's findById method.");
		logger.info(">>> Searching for entity in database.");
		
		Category categoryPersisted = categoryRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object Category not found."));
		
		logger.info(">>> The entity was found.");
		
		
		CategoryResponseDTO categoryDTO = categoryMapper.converterToDto(categoryPersisted);
		EntityModel<CategoryResponseDTO> response = CategoryMapper.addHateoasLinks(categoryDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public List<EntityModel<CategoryResponseDTO>> findAll() {
		logger.info(">>> Initializing the service's findAll method.");
		logger.info(">>> Searching for entities in the database.");
		
		List<Category> categorysPersisted = categoryRepository.findAll();
		
		logger.info(">>> The entities have been discovered.");
		
		List<CategoryResponseDTO> categoriesDTO = categorysPersisted.stream()
				.map((response) -> categoryMapper.converterToDto(response))
				.toList();
		
		List<EntityModel<CategoryResponseDTO>> responsesDTO = categoriesDTO.stream()
				.map((categoryDTO) -> CategoryMapper.addHateoasLinks(categoryDTO))
				.toList();
		
		logger.info(">>> Returning response.");
		
		return responsesDTO;
	}
	
	public EntityModel<CategoryResponseDTO> update(CategoryRequestDTO request ,Long id) {
		logger.info(">>> Initializing the service's update method.");
		logger.info(">>> Searching for entity in database.");
		
		Category categoryPersisted = categoryRepository.findById(id).orElseThrow(
				()-> new ObjectNotFoundException("Object Category not found."));
		
		Category categoryUpdated = updateData(categoryPersisted, request);
		
		CategoryResponseDTO categoryDTO = categoryMapper.converterToDto(categoryRepository.save(categoryUpdated));
		EntityModel<CategoryResponseDTO> response = CategoryMapper.addHateoasLinks(categoryDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public void delete(Long id) {
		logger.info(">>> Initializing the service's delete method.");
		/*Category categoryPersisted = categoryRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object Category not found."));
		*/
		
		logger.info(">>> Deleting Entity by ID");
		categoryRepository.deleteById(id);
	}
	
	public Category updateData(Category category, CategoryRequestDTO request) {
		logger.info(">>> Updating the data.");
		
		logger.debug(">>> Updating title.");
		category.setTitle(request.title());
		
		logger.debug(">>> Updating description.");
		category.setDescription(request.description());
		
		logger.info(">>> The data has been updated.");
		
		return category;
	}
	
	
}
