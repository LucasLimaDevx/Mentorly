package com.lucasdevx.Mentorly.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.controller.CourseController;
import com.lucasdevx.Mentorly.controller.LessonController;
import com.lucasdevx.Mentorly.dto.request.LessonRequestDTO;
import com.lucasdevx.Mentorly.dto.response.LessonResponseDTO;
import com.lucasdevx.Mentorly.model.Lesson;
import com.lucasdevx.Mentorly.service.UserService;

@Component
public class LessonMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public Lesson converterToEntity(LessonRequestDTO request) {
		logger.info(">>> Converting Lesson DTO to Entity.");
		Lesson lesson = new Lesson();
		
		logger.debug(">>> Setting title.");
		lesson.setTitle(request.title());
		
		logger.debug(">>> Setting description.");
		lesson.setDescription(request.description());
		
		logger.debug(">>> Setting videoUrl.");
		lesson.setVideoUrl(request.videoUrl());
		
		logger.debug(">>> Setting lessonOrder.");
		lesson.setLessonOrder(request.lessonOrder());
		
		logger.info(">>> The Lesson DTO conversion was successful.");
		return lesson;
	}
	
	public LessonResponseDTO converterToDto(Lesson lesson) {
		logger.info(">>> Converting Lesson Entity to DTO.");
		
		LessonResponseDTO response = new LessonResponseDTO(
									 lesson.getId(),
									 lesson.getTitle(),
									 lesson.getDescription(),
									 lesson.getVideoUrl(),
									 lesson.getLessonOrder());
		
		logger.info(">>> The Lesson Entity conversion was successful.");
		
		return response;
	}
	
	public EntityModel<LessonResponseDTO> addHateoasLinks(LessonResponseDTO lessonDTO, String role) {
		Long id = lessonDTO.id();
		
		logger.info(">>> Adding links HATEOAS.");
		
		if(role == null || role.equals("ADMIN")) {

			EntityModel<LessonResponseDTO> model =  EntityModel.of(lessonDTO,
					linkTo(methodOn(LessonController.class).findById(id, null)).withSelfRel().withType("GET"),
					linkTo(methodOn(LessonController.class).create(null)).withRel("create").withType("POST"),
					linkTo(methodOn(LessonController.class).update(null, id)).withRel("update").withType("PUT"),
					linkTo(methodOn(LessonController.class).delete(id)).withRel("delete").withType("DELETE"));
			
			logger.info(">>> The HATEOAS links have been successfully added.");
			
			return model;
		}
		
		EntityModel<LessonResponseDTO> model =  EntityModel.of(lessonDTO,
				linkTo(methodOn(LessonController.class).findById(id, null)).withSelfRel().withType("GET"));
	
		logger.info(">>> The HATEOAS links have been successfully added.");
		
		return model;
	}
	
	public EntityModel<LessonResponseDTO> addHateoasLinks(Long courseId, LessonResponseDTO lessonDTO, String role){
		logger.info(">>> Adding links HATEOAS.");
		
		if(role == null || role.equals("ADMIN")) {
			EntityModel<LessonResponseDTO> model =  EntityModel.of(lessonDTO,
				linkTo(methodOn(CourseController.class).findById(courseId, null)).withSelfRel().withType("GET"),
				linkTo(methodOn(CourseController.class).findAll(null)).withRel("findAll").withType("GET"),
				linkTo(methodOn(CourseController.class).findAllLessonsByCourseId(courseId, null)).withRel("findAllLessonsByCourseId").withType("GET"),
				linkTo(methodOn(CourseController.class).create(null)).withRel("create").withType("POST"),
				linkTo(methodOn(CourseController.class).update(null, courseId)).withRel("update").withType("PUT"),
				linkTo(methodOn(CourseController.class).delete(courseId)).withRel("delete").withType("DELETE"));
			
			logger.info(">>> The HATEOAS links have been successfully added.");
			return model;
		}
		
		EntityModel<LessonResponseDTO> model =  EntityModel.of(lessonDTO,
				linkTo(methodOn(CourseController.class).findById(courseId, null)).withSelfRel().withType("GET"),
				linkTo(methodOn(CourseController.class).findAll(null)).withRel("findAll").withType("GET"),
				linkTo(methodOn(CourseController.class).findAllLessonsByCourseId(courseId, null)).withRel("findAllLessonsByCourseId").withType("GET"));
		
		return model;
	}

}
