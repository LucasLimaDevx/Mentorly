package com.lucasdevx.Mentorly.controller;

import java.util.List;

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

@RestController
@RequestMapping("/categories/v1")
public class CategoryController {
	
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@PostMapping
	public CategoryResponseDTO create(@RequestBody CategoryRequestDTO request) {
		CategoryResponseDTO response = categoryService.create(request);
		return response;
	}
	
	@GetMapping("/{id}")
	public CategoryResponseDTO findById(@PathVariable Long id) {
		CategoryResponseDTO response = categoryService.findById(id);
		return response;
	}
	
	@GetMapping
	public List<CategoryResponseDTO> findAll() {
		List<CategoryResponseDTO> responsesDTO = categoryService.findAll();
		return responsesDTO;
	}
	
	@PutMapping("/{id}")
	public CategoryResponseDTO update(@RequestBody CategoryRequestDTO request, @PathVariable Long id) {
		CategoryResponseDTO response = categoryService.update(request, id);
		return response;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		categoryService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
