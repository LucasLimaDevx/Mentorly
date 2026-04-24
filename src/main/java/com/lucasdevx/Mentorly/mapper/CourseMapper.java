package com.lucasdevx.Mentorly.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.controller.CourseController;
import com.lucasdevx.Mentorly.dto.request.CourseRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
import com.lucasdevx.Mentorly.model.Course;
import com.lucasdevx.Mentorly.model.enums.Level;
import com.lucasdevx.Mentorly.service.UserService;

@Component
public class CourseMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private CategoryMapper categoryMapper;
	
	public CourseMapper(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
	}
	
	public Course converterToEntity(CourseRequestDTO request) {
		logger.info(">>> Converting Course DTO to Entity.");
		Course course = new Course();
		
		logger.debug(">>> Setting title.");
		course.setTitle(request.title());
		
		logger.debug(">>> Setting workloadHours.");
		course.setWorkloadHours(request.workloadHours());
		
		logger.debug(">>> Setting courseLevel");
		course.setLevel(Level.valueOf(request.courseLevel().toUpperCase()));
		
		logger.info(">>> The Course DTO conversion was successful.");
		
		return course;
	}
	
	public CourseResponseDTO converterToDto(Course course) {
		logger.info(">>> Converting Course Entity to DTO.");

		CourseResponseDTO response = new CourseResponseDTO(
									 course.getId(),
									 course.getTitle(),
									 course.getCreated(),
									 course.getWorkloadHours(),
									 course.isActive(),
									 course.getLevel().name(),
									 categoryMapper.converterToDto(course.getCategory()));
		
		logger.info(">>> The Course Entity conversion was successful.");
		return response;
		
	}
	
	public EntityModel<CourseResponseDTO> addHateoasLinks(CourseResponseDTO courseDTO, String role) {
		Long id = courseDTO.id();
		logger.info(">>> Adding links HATEOAS.");
		
		if(role == null || role.equals("ADMIN")) {
			EntityModel<CourseResponseDTO> model =  EntityModel.of(courseDTO,
				linkTo(methodOn(CourseController.class).findById(id, null)).withSelfRel().withType("GET"),
				linkTo(methodOn(CourseController.class).findAll(null)).withRel("findAll").withType("GET"),
				linkTo(methodOn(CourseController.class).findAllLessonsByCourseId(null, null)).withRel("findAllLessonsByCourseId").withType("GET"),
				linkTo(methodOn(CourseController.class).create(null)).withRel("create").withType("POST"),
				linkTo(methodOn(CourseController.class).update(null, id)).withRel("update").withType("PUT"),
				linkTo(methodOn(CourseController.class).delete(id)).withRel("delete").withType("DELETE"));
			
			logger.info(">>> The HATEOAS links have been successfully added.");
			return model;
		}
		
		EntityModel<CourseResponseDTO> model =  EntityModel.of(courseDTO,
				linkTo(methodOn(CourseController.class).findById(id, null)).withSelfRel().withType("GET"),
				linkTo(methodOn(CourseController.class).findAll(null)).withRel("findAll").withType("GET"));
		
		return model;
		
	}
}
