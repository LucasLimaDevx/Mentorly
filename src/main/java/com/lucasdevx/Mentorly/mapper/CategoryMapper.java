package com.lucasdevx.Mentorly.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.controller.CategoryController;
import com.lucasdevx.Mentorly.dto.request.CategoryRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CategoryResponseDTO;
import com.lucasdevx.Mentorly.model.Category;
import com.lucasdevx.Mentorly.service.UserService;

@Component
public class CategoryMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public Category converterToEntity(CategoryRequestDTO request) {
		logger.info(">>> Converting Category DTO to Entity.");
		
		Category category = new Category();
		
		logger.debug(">>> Setting title.");
		category.setTitle(request.title());
		
		logger.debug(">>> Setting description.");
		category.setDescription(request.description());
		
		logger.info(">>> The Category DTO conversion was successful.");
		
		return category;
	}
	
	public CategoryResponseDTO converterToDto(Category category) {
		logger.info(">>> Converting Category Entity to DTO.");
		
		CategoryResponseDTO response = new CategoryResponseDTO(
									   category.getId(),
									   category.getTitle(),
									   category.getDescription());
		
		logger.info(">>> The Category Entity conversion was successful.");
		
		return response;
	}
	
	public EntityModel<CategoryResponseDTO> addHateoasLinks(CategoryResponseDTO categoryDTO) {
		Long id = categoryDTO.id();
		logger.info(">>> Adding links HATEOAS.");
		EntityModel<CategoryResponseDTO> model =  EntityModel.of(categoryDTO,
				linkTo(methodOn(CategoryController.class).findById(id)).withSelfRel().withType("GET"),
				linkTo(methodOn(CategoryController.class).findAll()).withRel("findAll").withType("GET"),
				linkTo(methodOn(CategoryController.class).create(null)).withRel("create").withType("POST"),
				linkTo(methodOn(CategoryController.class).update(null, id)).withRel("update").withType("PUT"),
				linkTo(methodOn(CategoryController.class).delete(id)).withRel("delete").withType("DELETE"));
		
		logger.info(">>> The HATEOAS links have been successfully added.");
		
		return model;
		
	}

}
