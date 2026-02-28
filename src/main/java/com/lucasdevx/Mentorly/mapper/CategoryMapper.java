package com.lucasdevx.Mentorly.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.CategoryRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CategoryResponseDTO;
import com.lucasdevx.Mentorly.model.Category;
import com.lucasdevx.Mentorly.service.UserService;

@Component
public class CategoryMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public Category converterToEntity(CategoryRequestDTO request) {
		logger.info(">>> Converting DTO to Entity.");
		
		Category category = new Category();
		
		logger.debug(">>> Setting title.");
		category.setTitle(request.getTitle());
		
		logger.debug(">>> Setting description.");
		category.setDescription(request.getDescription());
		
		logger.info(">>> The DTO conversion was successful.");
		
		return category;
	}
	
	public CategoryResponseDTO converterToDto(Category category) {
		logger.info(">>> Converting Entity to DTO.");
		
		CategoryResponseDTO response = new CategoryResponseDTO(category);
		
		logger.info(">>> The Entity conversion was successful.");
		
		return response;
	}

}
