package com.lucasdevx.Mentorly.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.controller.CourseController;
import com.lucasdevx.Mentorly.dto.request.LessonRequestDTO;
import com.lucasdevx.Mentorly.dto.response.LessonResponseDTO;
import com.lucasdevx.Mentorly.exception.ObjectNotFoundException;
import com.lucasdevx.Mentorly.mapper.LessonMapper;
import com.lucasdevx.Mentorly.model.Course;
import com.lucasdevx.Mentorly.model.Lesson;
import com.lucasdevx.Mentorly.repository.CourseRepository;
import com.lucasdevx.Mentorly.repository.LessonRepository;

import jakarta.transaction.Transactional;

@Service
public class LessonService {

	private LessonRepository lessonRepository;
	private CourseRepository courseRepository;
	private LessonMapper lessonMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository,  LessonMapper lessonMapper ) {
		this.lessonRepository = lessonRepository;
		this.lessonMapper = lessonMapper;
		this.courseRepository = courseRepository;
	}
	
	public EntityModel<LessonResponseDTO> create(LessonRequestDTO request) {
		logger.info(">>> Initializing the service's create method.");
		
		Lesson lesson = lessonMapper.converterToEntity(request);
		
		logger.info(">>> Searching for Course entity in database.");
		Course coursePersisted = courseRepository.findById(request.courseId())
				.orElseThrow(()-> new ObjectNotFoundException("Object Course not found."));
		
		logger.debug(">>> Setting Course entity on lesson");
		lesson.setCourse(coursePersisted);
		
		logger.info(">>> Saving entity to database.");
		
		Lesson lessonPersisted = lessonRepository.save(lesson);
		
		logger.info(">>> The entity was saved in the database.");
		
		LessonResponseDTO lessonDTO = lessonMapper.converterToDto(lessonPersisted);
		EntityModel<LessonResponseDTO> response = lessonMapper.addHateoasLinks(lessonDTO, null);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public EntityModel<LessonResponseDTO> findById(Long id, String role) {
		logger.info(">>> Initializing the service's findById method.");
		logger.info(">>> Searching for entity in database.");
		
		Lesson lessonPersisted = lessonRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object Lesson not found."));
		
		logger.info(">>> The entity was found.");
		
		LessonResponseDTO lessonDTO = lessonMapper.converterToDto(lessonPersisted);
		EntityModel<LessonResponseDTO> response = lessonMapper.addHateoasLinks(lessonDTO, role);
		
		logger.info(">>> Returning response.");
		return response;
	}
	
	@Transactional
	public Set< EntityModel<LessonResponseDTO>>  findAllLessonsByCourseId(Long id, String role) {
		logger.info(">>> Initializing the service's findAllLessonsByCourseId method.");
		logger.info(">>> Searching for Course entity in database.");
		
		Course coursePersisted = courseRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object Course not found."));
		
		Set<Lesson> lessons = coursePersisted.getLessons();
		Set<LessonResponseDTO> lessonsDTO = lessons
				.stream()
				.map((lesson) -> lessonMapper.converterToDto(lesson))
				.collect(Collectors.toSet());
		
		Set<EntityModel<LessonResponseDTO>> responses = lessonsDTO
				.stream()
				.map((lessonDTO) -> {
					
					logger.info(">>> Adding links HATEOAS.");
					
					if(role == null || role.equals("ADMIN")) {
						EntityModel<LessonResponseDTO> model =  EntityModel.of(lessonDTO,
							linkTo(methodOn(CourseController.class).findById(id, null)).withSelfRel().withType("GET"),
							linkTo(methodOn(CourseController.class).findAll(null)).withRel("findAll").withType("GET"),
							linkTo(methodOn(CourseController.class).findAllLessonsByCourseId(null, null)).withRel("findAllLessonsByCourseId").withType("GET"),
							linkTo(methodOn(CourseController.class).create(null)).withRel("create").withType("POST"),
							linkTo(methodOn(CourseController.class).update(null, id)).withRel("update").withType("PUT"),
							linkTo(methodOn(CourseController.class).delete(id)).withRel("delete").withType("DELETE"));
						
						logger.info(">>> The HATEOAS links have been successfully added.");
						return model;
					}
					
					EntityModel<LessonResponseDTO> model =  EntityModel.of(lessonDTO,
							linkTo(methodOn(CourseController.class).findById(id, null)).withSelfRel().withType("GET"),
							linkTo(methodOn(CourseController.class).findAll(null)).withRel("findAll").withType("GET"),
							linkTo(methodOn(CourseController.class).findAllLessonsByCourseId(null, null)).withRel("findAllLessonsByCourseId").withType("GET"));
					
					return model;
				})
				.collect(Collectors.toSet());
		
		return responses;
		
		
	}
	
	public EntityModel<LessonResponseDTO> update(LessonRequestDTO request ,Long id) {
		logger.info(">>> Initializing the service's update method.");
		logger.info(">>> Searching for entity in database.");
		
		Lesson lessonPersisted = lessonRepository.findById(id).orElseThrow(
				()-> new ObjectNotFoundException("Object Lesson not found."));
		
		Lesson lessonUpdated = updateData(lessonPersisted, request);
	
		LessonResponseDTO lessonDTO = lessonMapper.converterToDto(lessonRepository.save(lessonUpdated));
		EntityModel<LessonResponseDTO> response = lessonMapper.addHateoasLinks(lessonDTO, null);
		
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
		
		if(!lesson.getCourse().getId().equals(request.courseId())) {
			logger.info(">>> Searching for Course entity in database.");
			Course coursePersisted = courseRepository.findById(request.courseId())
					.orElseThrow(()-> new ObjectNotFoundException("Object Course not found."));
			
			logger.debug(">>> Updating Course entity on lesson");
			lesson.setCourse(coursePersisted);
		}
		
		logger.info(">>> The data has been updated.");
		
		return lesson;
	}
	
}
