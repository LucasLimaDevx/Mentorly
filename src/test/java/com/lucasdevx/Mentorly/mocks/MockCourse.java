package com.lucasdevx.Mentorly.mocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lucasdevx.Mentorly.dto.request.CourseRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CategoryResponseDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
import com.lucasdevx.Mentorly.model.Category;
import com.lucasdevx.Mentorly.model.Course;
import com.lucasdevx.Mentorly.model.enums.Level;

public class MockCourse {
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public MockCourse() {
	}
	
	public Course MockEntity() throws ParseException {
		return mockEntity(0);
	}
	
	public CourseRequestDTO mockRequestDTO() throws ParseException {
		return mockRequestDTO(0);
	}
	
	public CourseResponseDTO MockResponseDTO() throws ParseException {
		return mockResponseDTO(0);
	}
	
	public List<Course> mockEntityList() throws ParseException{
		List<Course> list = new ArrayList<>();
		
		for(int i = 0 ; i < 14 ; i++) {
			list.add(mockEntity(i));
		}
		
		return list;
	}
	
	public List<CourseResponseDTO> mockResponseDTOList() throws ParseException{
		List<CourseResponseDTO> list = new ArrayList<>();
		
		for(int i = 0 ; i < 14 ; i++) {
			list.add(mockResponseDTO(i));
		}
		
		return list;
	}
	
	public Course mockEntity(Integer number) throws ParseException {
		MockCategory mock = new MockCategory();
		Category category = mock.mockEntity(number);
		Course course = new Course();
		Random random = new Random(3);
		
		category.setId(number.longValue());
		category.setDescription("Description Test" + number);
		category.setTitle("Title Test" + number);
		
		course.setId(number.longValue());
		course.setTitle("Title Test" + number);
		course.setWorkloadHours(30);	
		course.setActive((number % 2 == 0) ? true : false);
		course.setCreated(formatter.parse("12/07/2026 10:00"));
		course.setCategory(category);
		
		
		int randomNumberToLevel = random.nextInt(3);
		
		if(randomNumberToLevel == 0) {
			course.setLevel(Level.BEGINNER);
		}
		else if(randomNumberToLevel == 1) {
			course.setLevel(Level.INTERMEDIATE);
		}
		else {
			course.setLevel(Level.ADVANCED);
		}
		
		return course;
	}
	
	public CourseResponseDTO mockResponseDTO(Integer number) throws ParseException {
		MockCategory mock = new MockCategory();
		Random random = new Random(3);		
		String level = null;
		
		int randomNumberToLevel = random.nextInt(3);
		
		if(randomNumberToLevel == 0) {
			level = "BEGINNER";
		}
		else if(randomNumberToLevel == 1) {
			level = "INTERMEDIATE";
		}
		else {
			level = "ADVANCED";
		}
		
		CategoryResponseDTO categoryDTO = mock.mockResponseDTO(number);
		
		return new CourseResponseDTO(
						number.longValue(),
						"Title Test" + number,
						formatter.parse("12/07/2026 10:00"),
						30,
						(number % 2) == 0 ? true : false,
						level,
						categoryDTO);
	}
	
	public CourseRequestDTO mockRequestDTO(Integer number) throws ParseException {
		Random random = new Random(3);
		String level = null;
		
		int randomNumberToLevel = random.nextInt(3);
		 
		if(randomNumberToLevel == 0) {
			level = "BEGINNER";
		}
		else if(randomNumberToLevel == 1) {
			level = "INTERMEDIATE";
		}
		else {
			level = "ADVANCED";
		}
		return new CourseRequestDTO(
						"Title Test" + number,
						30,
						(number % 2) == 0 ? true : false,
						level,
						number.longValue());
	}
	
}
