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

import com.lucasdevx.Mentorly.dto.request.LessonRequestDTO;
import com.lucasdevx.Mentorly.dto.response.LessonResponseDTO;
import com.lucasdevx.Mentorly.service.LessonService;

@RestController
@RequestMapping("/lessons/v1")
public class LessonController {
	
	private LessonService lessonService;

	public LessonController(LessonService lessonService) {
		this.lessonService = lessonService;
	}
	
	@PostMapping
	public LessonResponseDTO create(@RequestBody LessonRequestDTO request) {
		LessonResponseDTO response = lessonService.create(request);
		return response;
	}
	
	@GetMapping("/{id}")
	public LessonResponseDTO findById(@PathVariable Long id) {
		LessonResponseDTO response = lessonService.findById(id);
		return response;
	}
	
	@GetMapping
	public List<LessonResponseDTO> findAll() {
		List<LessonResponseDTO> responsesDTO = lessonService.findAll();
		return responsesDTO;
	}
	
	@PutMapping("/{id}")
	public LessonResponseDTO update(@RequestBody LessonRequestDTO request, @PathVariable Long id) {
		LessonResponseDTO response = lessonService.update(request, id);
		return response;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		lessonService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
