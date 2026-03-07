package com.lucasdevx.Mentorly.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.controller.LessonController;
import com.lucasdevx.Mentorly.dto.request.LessonRequestDTO;
import com.lucasdevx.Mentorly.dto.response.LessonResponseDTO;
import com.lucasdevx.Mentorly.exception.ObjectNotFoundException;
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
	
	public EntityModel<LessonResponseDTO> create(LessonRequestDTO request) {
		logger.info(">>> Initializing the service's create method.");
		
		Lesson lesson = lessonMapper.converterToEntity(request);
		
		logger.info(">>> Saving entity to database.");
		
		Lesson lessonPersisted = lessonRepository.save(lesson);
		
		logger.info(">>> The entity was saved in the database.");
		
		LessonResponseDTO lessonDTO = lessonMapper.converterToDto(lessonPersisted);
		EntityModel<LessonResponseDTO> response = addHateoasLinks(lessonDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public EntityModel<LessonResponseDTO> findById(Long id) {
		logger.info(">>> Initializing the service's findById method.");
		logger.info(">>> Searching for entity in database.");
		
		Lesson lessonPersisted = lessonRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object Lesson not found."));
		
		logger.info(">>> The entity was found.");
		
		LessonResponseDTO lessonDTO = lessonMapper.converterToDto(lessonPersisted);
		EntityModel<LessonResponseDTO> response = addHateoasLinks(lessonDTO);
		
		logger.info(">>> Returning response.");
		return response;
	}
	
	public List<EntityModel<LessonResponseDTO>> findAll() {
		logger.info(">>> Initializing the service's findAll method.");
		logger.info(">>> Searching for entities in the database.");
		
		List<Lesson> lessonsPersisted = lessonRepository.findAll();
		
		logger.info(">>> The entities have been discovered.");
		
		List<LessonResponseDTO> lessonsDTO = lessonsPersisted.stream()
				.map((response) -> lessonMapper.converterToDto(response))
				.toList();
		
		List<EntityModel<LessonResponseDTO>> responsesDTO = lessonsDTO.stream()
				.map((lessonDTO) -> addHateoasLinks(lessonDTO))
				.toList();
		
		logger.info(">>> Returning response.");
		return responsesDTO;
	}
	
	public EntityModel<LessonResponseDTO> update(LessonRequestDTO request ,Long id) {
		logger.info(">>> Initializing the service's update method.");
		logger.info(">>> Searching for entity in database.");
		
		Lesson lessonPersisted = lessonRepository.findById(id).orElseThrow(
				()-> new ObjectNotFoundException("Object Lesson not found."));
		
		Lesson lessonUpdated = updateData(lessonPersisted, request);
	
		LessonResponseDTO lessonDTO = lessonMapper.converterToDto(lessonRepository.save(lessonUpdated));
		EntityModel<LessonResponseDTO> response = addHateoasLinks(lessonDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public void delete(Long id) {
		logger.info(">>> Initializing the service's delete method.");
		/*Lesson lessonPersisted = lessonRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object Lesson not found."));
		*/
		
		logger.info(">>> Deleting Entity by ID");
		lessonRepository.deleteById(id);
	}
	
	public Lesson updateData(Lesson lesson, LessonRequestDTO request) {
		logger.info(">>> Updating the data.");
		
		logger.debug(">>> Updating title.");
		lesson.setTitle(request.title());
		
		logger.debug(">>> Updating description.");
		lesson.setDescription(request.description());
		
		logger.debug(">>> Updating videoUrl.");
		lesson.setVideoUrl(request.videoUrl());
		
		logger.debug(">>> Updating lessonOrder.");
		lesson.setLessonOrder(request.lessonOrder());
		
		logger.info(">>> The data has been updated.");
		
		return lesson;
	}
	
	public EntityModel<LessonResponseDTO> addHateoasLinks(LessonResponseDTO lessonDTO) {
		Long id = lessonDTO.getId();
		logger.info(">>> Adding links HATEOAS.");
		EntityModel<LessonResponseDTO> model =  EntityModel.of(lessonDTO,
				linkTo(methodOn(LessonController.class).findById(id)).withSelfRel().withType("GET"),
				linkTo(methodOn(LessonController.class).findAll()).withRel("findAll").withType("GET"),
				linkTo(methodOn(LessonController.class).create(null)).withRel("create").withType("POST"),
				linkTo(methodOn(LessonController.class).update(null, id)).withRel("update").withType("PUT"),
				linkTo(methodOn(LessonController.class).delete(id)).withRel("delete").withType("DELETE"));
		
		logger.info(">>> The HATEOAS links have been successfully added.");
		
		return model;
		
	}
}
