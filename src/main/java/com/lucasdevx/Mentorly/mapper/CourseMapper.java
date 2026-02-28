package com.lucasdevx.Mentorly.mapper;

import org.springframework.stereotype.Component;

import com.lucasdevx.Mentorly.dto.request.CourseRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
import com.lucasdevx.Mentorly.model.Course;

@Component
public class CourseMapper {
	public Course converterToEntity(CourseRequestDTO request) {
		Course course = new Course();
		
		course.setTitle(request.getTitle());
		course.setWorkloadHours(request.getWorkloadHours());
	
		return course;
	}
	
	public CourseResponseDTO converterToDto(Course course) {
		return new CourseResponseDTO(course);
	}
}
