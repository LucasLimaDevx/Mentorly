package com.lucasdevx.Mentorly.service;

import java.util.Date;
import java.util.List;

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
	
	public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
		this.courseRepository = courseRepository;
		this.courseMapper = courseMapper;
	}
	
	public CourseResponseDTO create(CourseRequestDTO request) {
		
		Course course = courseMapper.converterToEntity(request);
		
		course.setCreated(new Date());
		course.setActive(true);
		
		Course coursePersisted = courseRepository.save(course);
		
		CourseResponseDTO response = courseMapper.converterToDto(coursePersisted);
		
		return response;
	}
	
	public CourseResponseDTO findById(Long id) {
		Course coursePersisted = courseRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		
		CourseResponseDTO response = courseMapper.converterToDto(coursePersisted);
		
		return response;
	}
	
	public List<CourseResponseDTO> findAll() {
		List<Course> coursesPersisted = courseRepository.findAll();
		
		List<CourseResponseDTO> responsesDTO = coursesPersisted.stream()
				.map((response) -> courseMapper.converterToDto(response))
				.toList();
		
		return responsesDTO;
	}
	
	public CourseResponseDTO update(CourseRequestDTO request ,Long id) {
		Course coursePersisted = courseRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Invalid ID"));
		
		Course courseUpdated = updateDate(coursePersisted, request);
		
		CourseResponseDTO response = courseMapper.converterToDto(courseRepository.save(courseUpdated));
		
		return response;
	}
	
	public void delete(Long id) {
		/*Course coursePersisted = courseRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		*/
		courseRepository.deleteById(id);
	}
	
	public Course updateDate(Course course, CourseRequestDTO request) {
		
		course.setTitle(request.getTitle());
		course.setWorkloadHours(request.getWorkloadHours());
		
		if(request.getActive() != null) {
			course.setActive(request.getActive());
		}
		
		
		return course;
	}
}
