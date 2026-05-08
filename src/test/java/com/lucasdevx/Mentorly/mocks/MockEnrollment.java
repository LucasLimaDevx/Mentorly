package com.lucasdevx.Mentorly.mocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.lucasdevx.Mentorly.dto.request.EnrollmentRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
import com.lucasdevx.Mentorly.dto.response.EnrollmentResponseDTO;
import com.lucasdevx.Mentorly.model.Course;
import com.lucasdevx.Mentorly.model.Enrollment;
import com.lucasdevx.Mentorly.model.User;

public class MockEnrollment {
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public MockEnrollment() {
	}
	
	public Enrollment MockEntity() throws ParseException {
		return mockEntity(0);
	}
	
	public EnrollmentRequestDTO mockRequestDTO() throws ParseException {
		return mockRequestDTO(0);
	}
	
	public EnrollmentResponseDTO MockResponseDTO() throws ParseException {
		return mockResponseDTO(0);
	}
	
	public List<Enrollment> mockEntityList() throws ParseException{
		List<Enrollment> list = new ArrayList<>();
		
		for(int i = 0 ; i < 14 ; i++) {
			list.add(mockEntity(i));
		}
		
		return list;
	}
	
	public List<EnrollmentResponseDTO> mockResponseDTOList() throws ParseException{
		List<EnrollmentResponseDTO> list = new ArrayList<>();
		
		for(int i = 0 ; i < 14 ; i++) {
			list.add(mockResponseDTO(i));
		}
		
		return list;
	}
	
	public Enrollment mockEntity(Integer number) throws ParseException {
		MockUser mockUser = new MockUser();
		MockCourse mockCourse = new MockCourse();
		
		User user = mockUser.mockEntity(number);
		Course course = mockCourse.mockEntity(number);
		
		Enrollment enrollment = new Enrollment();
		
		enrollment.setId(number.longValue());
		enrollment.setProgressPercentage((number % 2 == 0) ? 50 : 300);
		enrollment.setEnrollmentDate(formatter.parse("12/07/2026 10:00"));
		enrollment.setUser(user);
		enrollment.setCourse(course);
		
		return enrollment;
	}
	
	public EnrollmentResponseDTO mockResponseDTO(Integer number) throws ParseException {	
		MockCourse mock = new MockCourse();
		CourseResponseDTO courseDTO = mock.mockResponseDTO(number);
		
		return new EnrollmentResponseDTO(
						number.longValue(),
						formatter.parse("12/07/2026 10:00"),
						(number % 2 == 0) ? 50 : 300,
						courseDTO);
	}
	
	public EnrollmentRequestDTO mockRequestDTO(Integer number) throws ParseException {
		return new EnrollmentRequestDTO((number % 2 == 0) ? 50 : 300, number.longValue());
	}
	
}
