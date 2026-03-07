package com.lucasdevx.Mentorly.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.controller.CourseController;
import com.lucasdevx.Mentorly.dto.request.CourseRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
import com.lucasdevx.Mentorly.exception.ObjectNotFoundException;
import com.lucasdevx.Mentorly.mapper.CourseMapper;
import com.lucasdevx.Mentorly.model.Course;
import com.lucasdevx.Mentorly.repository.CourseRepository;

@Service
public class CourseService {

	private CourseRepository courseRepository;
	private CourseMapper courseMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
		this.courseRepository = courseRepository;
		this.courseMapper = courseMapper;
	}
	
	public  EntityModel<CourseResponseDTO> create(CourseRequestDTO request) {
		logger.info(">>> Initializing the service's create method.");
		
		Course course = courseMapper.converterToEntity(request);
		
		logger.debug(">>> Setting created.");
		course.setCreated(new Date());
		
		logger.debug(">>> Setting active.");
		course.setActive(true);
		
		logger.info(">>> Saving entity to database.");
		
		Course coursePersisted = courseRepository.save(course);
		
		logger.info(">>> The entity was saved in the database.");
		
		CourseResponseDTO courseDTO = courseMapper.converterToDto(coursePersisted);
		EntityModel<CourseResponseDTO> response = addHateoasLinks(courseDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public  EntityModel<CourseResponseDTO> findById(Long id) {
		logger.info(">>> Initializing the service's findById method.");
		logger.info(">>> Searching for entity in database.");
		
		Course coursePersisted = courseRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object Course not found."));
		
		logger.info(">>> The entity was found.");
		
		CourseResponseDTO courseDTO = courseMapper.converterToDto(coursePersisted);
		EntityModel<CourseResponseDTO> response = addHateoasLinks(courseDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public List< EntityModel<CourseResponseDTO>> findAll() {
		logger.info(">>> Initializing the service's findAll method.");
		logger.info(">>> Searching for entities in the database.");
		
		List<Course> coursesPersisted = courseRepository.findAll();
		
		logger.info(">>> The entities have been discovered.");
		
		List<CourseResponseDTO> coursesDTO = coursesPersisted.stream()
				.map((response) -> courseMapper.converterToDto(response))
				.toList();
		
		List<EntityModel<CourseResponseDTO>> responsesDTO = coursesDTO.stream()
				.map((courseDTO) -> addHateoasLinks(courseDTO))
				.toList();
		
		logger.info(">>> Returning response.");
		
		return responsesDTO;
	}
	
	public  EntityModel<CourseResponseDTO> update(CourseRequestDTO request ,Long id) {
		logger.info(">>> Initializing the service's update method.");
		logger.info(">>> Searching for entity in database.");
		
		Course coursePersisted = courseRepository.findById(id).orElseThrow(
				()-> new ObjectNotFoundException("Object Course not found."));
		
		Course courseUpdated = updateData(coursePersisted, request);
		
		CourseResponseDTO courseDTO = courseMapper.converterToDto(courseRepository.save(courseUpdated));
		EntityModel<CourseResponseDTO> response = addHateoasLinks(courseDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public void delete(Long id) {
		logger.info(">>> Initializing the service's delete method.");
		/*Course coursePersisted = courseRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object Course not found."));
		*/
		
		logger.info(">>> The data has been updated.");
		courseRepository.deleteById(id);
	}
	
	public Course updateData(Course course, CourseRequestDTO request) {
		logger.info(">>> Updating the data.");
		
		logger.debug(">>> Setting title.");
		course.setTitle(request.title());
		
		logger.debug(">>> Setting workloadHours.");
		course.setWorkloadHours(request.workloadHours());
		
		logger.debug(">>> Checking if the active property is null.");
		if(request.active() != null) {
			course.setActive(request.active());
		}
		
		logger.info(">>> The data has been updated.");
		return course;
	}
	
	public EntityModel<CourseResponseDTO> addHateoasLinks(CourseResponseDTO courseDTO) {
		Long id = courseDTO.getId();
		logger.info(">>> Adding links HATEOAS.");
		EntityModel<CourseResponseDTO> model =  EntityModel.of(courseDTO,
				linkTo(methodOn(CourseController.class).findById(id)).withSelfRel().withType("GET"),
				linkTo(methodOn(CourseController.class).findAll()).withRel("findAll").withType("GET"),
				linkTo(methodOn(CourseController.class).create(null)).withRel("create").withType("POST"),
				linkTo(methodOn(CourseController.class).update(null, id)).withRel("update").withType("PUT"),
				linkTo(methodOn(CourseController.class).delete(id)).withRel("delete").withType("DELETE"));
		
		logger.info(">>> The HATEOAS links have been successfully added.");
		
		return model;
		
	}
}
