package com.lucasdevx.Mentorly.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.controller.EnrollmentController;
import com.lucasdevx.Mentorly.dto.request.EnrollmentRequestDTO;
import com.lucasdevx.Mentorly.dto.response.EnrollmentResponseDTO;
import com.lucasdevx.Mentorly.exception.ObjectNotFoundException;
import com.lucasdevx.Mentorly.mapper.EnrollmentMapper;
import com.lucasdevx.Mentorly.model.Course;
import com.lucasdevx.Mentorly.model.Enrollment;
import com.lucasdevx.Mentorly.model.User;
import com.lucasdevx.Mentorly.repository.CourseRepository;
import com.lucasdevx.Mentorly.repository.EnrollmentRepository;
import com.lucasdevx.Mentorly.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class EnrollmentService {

	private EnrollmentRepository enrollmentRepository;
	private CourseRepository courseRepository;
	private UserRepository userRepository;
	private EnrollmentMapper enrollmentMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public EnrollmentService(EnrollmentRepository enrollmentRepository, UserRepository userRepository, CourseRepository courseRepository, EnrollmentMapper enrollmentMapper) {
		this.enrollmentRepository = enrollmentRepository;
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
		this.enrollmentMapper = enrollmentMapper;
		
	}
	
	@Transactional
	public EntityModel<EnrollmentResponseDTO> create(EnrollmentRequestDTO request) {
		logger.info(">>> Initializing the service's create method.");
		
		Enrollment enrollment = enrollmentMapper.converterToEntity(request);
		
		logger.debug(">>> Setting enrollmenDate");
		enrollment.setEnrollmentDate(new Date());
		
		logger.info(">>> Searching for Course entity in database.");
		Course coursePersisted = courseRepository.findById(request.courseId())
				.orElseThrow(()-> new ObjectNotFoundException("Object Course Not Found"));
		
		logger.info(">>> Searching for User entity in database.");
		User userPersisted = userRepository.findById(request.userId())
				.orElseThrow(()-> new ObjectNotFoundException("Object User Not Found"));
		
		logger.debug(">>> Setting user");
		enrollment.setUser(userPersisted);
		
		logger.debug(">>> Setting course");
		enrollment.setCourse(coursePersisted);
		
		logger.info(">>> Saving entity to database.");
		
		Enrollment enrollmentPersisted = enrollmentRepository.save(enrollment);

		logger.info(">>> The entity was saved in the database.");
		
		EnrollmentResponseDTO enrollmentDTO = enrollmentMapper.converterToDto(enrollmentPersisted);
		EntityModel<EnrollmentResponseDTO> response = addHateoasLinks(enrollmentDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public EntityModel<EnrollmentResponseDTO> findById(Long id) {
		logger.info(">>> Initializing the service's findById method.");
		logger.info(">>> Searching for entity in database.");
		Enrollment enrollmentPersisted = enrollmentRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object Enrollment Not Found"));
		
		
		logger.info(">>> The entity was found.");
		
		EnrollmentResponseDTO enrollmentDTO = enrollmentMapper.converterToDto(enrollmentPersisted);
		EntityModel<EnrollmentResponseDTO> response = addHateoasLinks(enrollmentDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public List<EntityModel<EnrollmentResponseDTO>> findAll() {
		logger.info(">>> Initializing the service's findAll method.");
		logger.info(">>> Searching for entities in the database.");
		
		List<Enrollment> enrollmentsPersisted = enrollmentRepository.findAll();
		
		logger.info(">>> The entities have been discovered.");
		
		List<EnrollmentResponseDTO> enrollmentsDTO = enrollmentsPersisted.stream()
				.map((response) -> enrollmentMapper.converterToDto(response))
				.toList();
		
		List<EntityModel<EnrollmentResponseDTO>> responsesDTO = enrollmentsDTO.stream()
				.map((enrollmentDTO) -> addHateoasLinks(enrollmentDTO))
				.toList();
		
		logger.info(">>> Returning response.");
		
		return responsesDTO;
	}
	
	public EntityModel<EnrollmentResponseDTO> update(EnrollmentRequestDTO request ,Long id) {
		logger.info(">>> Initializing the service's update method.");
		logger.info(">>> Searching for entity in database.");
		
		
		Enrollment enrollmentPersisted = enrollmentRepository.findById(id).orElseThrow(
				()-> new ObjectNotFoundException("Object Enrollment Not Found."));
		
		Enrollment enrollmentUpdated = updateData(enrollmentPersisted, request);
		
		EnrollmentResponseDTO enrollmentDTO = enrollmentMapper.converterToDto(enrollmentRepository.save(enrollmentUpdated));
		EntityModel<EnrollmentResponseDTO> response = addHateoasLinks(enrollmentDTO);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public void delete(Long id) {
		logger.info(">>> Initializing the service's delete method.");
		/*Enrollment enrollmentPersisted = enrollmentRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Object Enrollment Not Found"));
		*/
		
		logger.info(">>> Deleting Entity by ID");
		enrollmentRepository.deleteById(id);
	}
	
	public Enrollment updateData(Enrollment enrollment, EnrollmentRequestDTO request) {
		logger.info(">>> Updating the data.");
		
		enrollment.setProgressPercentage(request.progressPercentage());
		
		if(!enrollment.getCourse().getId().equals(request.courseId())) {
			logger.info(">>> Searching for Course entity in database.");
			Course coursePersisted = courseRepository.findById(request.courseId())
					.orElseThrow(()-> new ObjectNotFoundException("Object Course not found."));
			
			logger.debug(">>> Updating Category entity on lesson");
			enrollment.setCourse(coursePersisted);
		}
		
		if(!enrollment.getUser().getId().equals(request.userId())) {
			logger.info(">>> Searching for User entity in database.");
			User userPersisted = userRepository.findById(request.userId())
					.orElseThrow(()-> new ObjectNotFoundException("Object User not found."));
			
			logger.debug(">>> Updating User entity on lesson");
			enrollment.setUser(userPersisted);
		}
		
		
		logger.info(">>> The data has been updated.");
		
		return enrollment;
	}
	
	public EntityModel<EnrollmentResponseDTO> addHateoasLinks(EnrollmentResponseDTO enrollmentDTO) {
		Long id = enrollmentDTO.id();
		logger.info(">>> Adding links HATEOAS.");
		EntityModel<EnrollmentResponseDTO> model =  EntityModel.of(enrollmentDTO,
				linkTo(methodOn(EnrollmentController.class).findById(id)).withSelfRel().withType("GET"),
				linkTo(methodOn(EnrollmentController.class).findAll()).withRel("findAll").withType("GET"),
				linkTo(methodOn(EnrollmentController.class).create(null)).withRel("create").withType("POST"),
				linkTo(methodOn(EnrollmentController.class).update(null, id)).withRel("update").withType("PUT"),
				linkTo(methodOn(EnrollmentController.class).delete(id)).withRel("delete").withType("DELETE"));
		
		logger.info(">>> The HATEOAS links have been successfully added.");
		
		return model;
		
	}
}
