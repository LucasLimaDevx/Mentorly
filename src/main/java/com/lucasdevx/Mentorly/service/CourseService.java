package com.lucasdevx.Mentorly.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.dto.request.CourseRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
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
	
	public CourseResponseDTO create(CourseRequestDTO request) {
		logger.info(">>> Initializing the service's create method.");
		
		Course course = courseMapper.converterToEntity(request);
		
		logger.debug(">>> Setting created.");
		course.setCreated(new Date());
		
		logger.debug(">>> Setting active.");
		course.setActive(true);
		
		logger.info(">>> Saving entity to database.");
		
		Course coursePersisted = courseRepository.save(course);
		
		logger.info(">>> The entity was saved in the database.");
		
		CourseResponseDTO response = courseMapper.converterToDto(coursePersisted);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public CourseResponseDTO findById(Long id) {
		logger.info(">>> Initializing the service's findById method.");
		logger.info(">>> Searching for entity in database.");
		
		Course coursePersisted = courseRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		
		logger.info(">>> The entity was found.");
		
		CourseResponseDTO response = courseMapper.converterToDto(coursePersisted);
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public List<CourseResponseDTO> findAll() {
		logger.info(">>> Initializing the service's findAll method.");
		logger.info(">>> Searching for entities in the database.");
		
		List<Course> coursesPersisted = courseRepository.findAll();
		
		logger.info(">>> The entities have been discovered.");
		
		List<CourseResponseDTO> responsesDTO = coursesPersisted.stream()
				.map((response) -> courseMapper.converterToDto(response))
				.toList();
		
		logger.info(">>> Returning response.");
		
		return responsesDTO;
	}
	
	public CourseResponseDTO update(CourseRequestDTO request ,Long id) {
		logger.info(">>> Initializing the service's update method.");
		logger.info(">>> Searching for entity in database.");
		
		Course coursePersisted = courseRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Invalid ID"));
		
		Course courseUpdated = updateData(coursePersisted, request);
		
		CourseResponseDTO response = courseMapper.converterToDto(courseRepository.save(courseUpdated));
		
		logger.info(">>> Returning response.");
		
		return response;
	}
	
	public void delete(Long id) {
		logger.info(">>> Initializing the service's delete method.");
		/*Course coursePersisted = courseRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		*/
		
		logger.info(">>> The data has been updated.");
		courseRepository.deleteById(id);
	}
	
	public Course updateData(Course course, CourseRequestDTO request) {
		logger.info(">>> Updating the data.");
		
		logger.debug(">>> Setting title.");
		course.setTitle(request.getTitle());
		
		logger.debug(">>> Setting workloadHours.");
		course.setWorkloadHours(request.getWorkloadHours());
		
		logger.debug(">>> Checking if the active property is null.");
		if(request.getActive() != null) {
			course.setActive(request.getActive());
		}
		
		logger.info(">>> The data has been updated.");
		return course;
	}
}
