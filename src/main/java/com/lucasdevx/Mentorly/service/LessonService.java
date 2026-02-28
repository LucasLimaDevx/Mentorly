package com.lucasdevx.Mentorly.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.dto.request.LessonRequestDTO;
import com.lucasdevx.Mentorly.dto.response.LessonResponseDTO;
import com.lucasdevx.Mentorly.mapper.LessonMapper;
import com.lucasdevx.Mentorly.model.Lesson;
import com.lucasdevx.Mentorly.repository.LessonRepository;

@Service
public class LessonService {

	private LessonRepository lessonRepository;
	private LessonMapper lessonMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper) {
		this.lessonRepository = lessonRepository;
		this.lessonMapper = lessonMapper;
	}
	
	public LessonResponseDTO create(LessonRequestDTO request) {
		logger.info(">>> Initializing the service's create method.");
		
		Lesson lesson = lessonMapper.converterToEntity(request);
		
		logger.info(">>> Saving entity to database.");
		
		Lesson lessonPersisted = lessonRepository.save(lesson);
		
		logger.info(">>> The entity was saved in the database.");
		
		LessonResponseDTO response = lessonMapper.converterToDto(lessonPersisted);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public LessonResponseDTO findById(Long id) {
		logger.info(">>> Initializing the service's findById method.");
		logger.info(">>> Searching for entity in database.");
		
		Lesson lessonPersisted = lessonRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		
		logger.info(">>> The entity was found.");
		
		LessonResponseDTO response = lessonMapper.converterToDto(lessonPersisted);
		
		logger.info(">>> Returning response.");
		return response;
	}
	
	public List<LessonResponseDTO> findAll() {
		logger.info(">>> Initializing the service's findAll method.");
		logger.info(">>> Searching for entities in the database.");
		
		List<Lesson> lessonsPersisted = lessonRepository.findAll();
		
		logger.info(">>> The entities have been discovered.");
		
		List<LessonResponseDTO> responsesDTO = lessonsPersisted.stream()
				.map((response) -> lessonMapper.converterToDto(response))
				.toList();
		
		logger.info(">>> Returning response.");
		return responsesDTO;
	}
	
	public LessonResponseDTO update(LessonRequestDTO request ,Long id) {
		logger.info(">>> Initializing the service's update method.");
		logger.info(">>> Searching for entity in database.");
		
		Lesson lessonPersisted = lessonRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Invalid ID"));
		
		Lesson lessonUpdated = updateData(lessonPersisted, request);
	
		LessonResponseDTO response = lessonMapper.converterToDto(lessonRepository.save(lessonUpdated));
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public void delete(Long id) {
		logger.info(">>> Initializing the service's delete method.");
		/*Lesson lessonPersisted = lessonRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		*/
		
		logger.info(">>> Deleting Entity by ID");
		lessonRepository.deleteById(id);
	}
	
	public Lesson updateData(Lesson lesson, LessonRequestDTO request) {
		logger.info(">>> Updating the data.");
		
		logger.debug(">>> Updating title.");
		lesson.setTitle(request.getTitle());
		
		logger.debug(">>> Updating description.");
		lesson.setDescription(request.getDescription());
		
		logger.debug(">>> Updating videoUrl.");
		lesson.setVideoUrl(request.getVideoUrl());
		
		logger.debug(">>> Updating lessonOrder.");
		lesson.setLessonOrder(request.getLessonOrder());
		
		logger.info(">>> The data has been updated.");
		
		return lesson;
	}
}
