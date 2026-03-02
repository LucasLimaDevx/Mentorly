package com.lucasdevx.Mentorly.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdevx.Mentorly.dto.request.CategoryRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CategoryResponseDTO;
import com.lucasdevx.Mentorly.service.CategoryService;
import com.lucasdevx.Mentorly.service.UserService;

@RestController
@RequestMapping("/categories/v1")
public class CategoryController {
	
	private CategoryService categoryService;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@PostMapping(
			consumes = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
							},
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				})
	public EntityModel<CategoryResponseDTO> create(@RequestBody CategoryRequestDTO request) {
		logger.info(">>> Initializing the controller's create method.");
		
		EntityModel<CategoryResponseDTO> response = categoryService.create(request);
		
		logger.info(">>> Finishing the controller's create method.");
		
		return response;
	}
	
	@GetMapping(
			value = "/{id}",
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				})
	public EntityModel<CategoryResponseDTO> findById(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's findById method.");
		
		EntityModel<CategoryResponseDTO> response = categoryService.findById(id);
		
		logger.info(">>> Finishing the controller's findById method.");
		
		return response;
	}
	
	@GetMapping(
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				})
	public List<EntityModel<CategoryResponseDTO>> findAll() {
		logger.info(">>> Initializing the controller's findAll method.");
		
		List<EntityModel<CategoryResponseDTO>> responsesDTO = categoryService.findAll();
		
		logger.info(">>> Finishing the controller's findAll method.");
		
		return responsesDTO;
	}
	
	@PutMapping(value = "/{id}",
			consumes = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
							},
			produces = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_YAML_VALUE
				})
	public EntityModel<CategoryResponseDTO> update(@RequestBody CategoryRequestDTO request, @PathVariable Long id) {
		logger.info(">>> Initializing the controller's update method.");
		
		EntityModel<CategoryResponseDTO> response = categoryService.update(request, id);
		
		logger.info(">>> Finishing the controller's update method.");
		
		return response;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's delete method.");
		
		categoryService.delete(id);
		
		logger.info(">>> Finishing the controller's delete method.");
		
		return ResponseEntity.noContent().build();
	}

}
