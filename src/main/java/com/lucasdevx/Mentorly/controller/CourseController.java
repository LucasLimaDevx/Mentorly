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

import com.lucasdevx.Mentorly.dto.request.CourseRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
import com.lucasdevx.Mentorly.service.CourseService;

@RestController
@RequestMapping("/courses/v1")
public class CourseController {
	
	private CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}
	
	@PostMapping
	public CourseResponseDTO create(@RequestBody CourseRequestDTO request) {
		CourseResponseDTO response = courseService.create(request);
		return response;
	}
	
	@GetMapping("/{id}")
	public CourseResponseDTO findById(@PathVariable Long id) {
		CourseResponseDTO response = courseService.findById(id);
		return response;
	}
	
	@GetMapping
	public List<CourseResponseDTO> findAll() {
		List<CourseResponseDTO> responsesDTO = courseService.findAll();
		return responsesDTO;
	}
	
	@PutMapping("/{id}")
	public CourseResponseDTO update(@RequestBody CourseRequestDTO request, @PathVariable Long id) {
		CourseResponseDTO response = courseService.update(request, id);
		return response;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		courseService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
