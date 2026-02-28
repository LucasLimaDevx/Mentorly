package com.lucasdevx.Mentorly.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.lucasdevx.Mentorly.service.UserService;

@RestController
@RequestMapping("/lessons/v1")
public class LessonController {
	
	private LessonService lessonService;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public LessonController(LessonService lessonService) {
		this.lessonService = lessonService;
	}
	
	@PostMapping
	public LessonResponseDTO create(@RequestBody LessonRequestDTO request) {
		logger.info(">>> Initializing the controller's create method.");
		
		LessonResponseDTO response = lessonService.create(request);
		
		logger.info(">>> Finishing the controller's create method.");
		return response;
	}
	
	@GetMapping("/{id}")
	public LessonResponseDTO findById(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's findById method.");
		
		LessonResponseDTO response = lessonService.findById(id);
		
		logger.info(">>> Finishing the controller's findById method.");
		
		return response;
	}
	
	@GetMapping
	public List<LessonResponseDTO> findAll() {
		logger.info(">>> Initializing the controller's findAll method.");
		
		List<LessonResponseDTO> responsesDTO = lessonService.findAll();
		
		logger.info(">>> Finishing the controller's findAll method.");
		
		return responsesDTO;
	}
	
	@PutMapping("/{id}")
	public LessonResponseDTO update(@RequestBody LessonRequestDTO request, @PathVariable Long id) {
		logger.info(">>> Initializing the controller's update method.");
		
		LessonResponseDTO response = lessonService.update(request, id);
		
		logger.info(">>> Finishing the controller's update method.");
		
		return response;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info(">>> Initializing the controller's delete method.");
		
		lessonService.delete(id);
		
		logger.info(">>> Finishing the controller's delete method.");
		
		return ResponseEntity.noContent().build();
	}

}
