package com.lucasdevx.Mentorly.mapper;

import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.CategoryRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CategoryResponseDTO;
import com.lucasdevx.Mentorly.model.Category;

@Component
public class CategoryMapper {
	public Category converterToEntity(CategoryRequestDTO request) {
		Category category = new Category();
		
		category.setTitle(request.getTitle());
		category.setDescription(request.getDescription());
		
		return category;
	}
	
	public CategoryResponseDTO converterToDto(Category category) {
		return new CategoryResponseDTO(category);
	}

}
