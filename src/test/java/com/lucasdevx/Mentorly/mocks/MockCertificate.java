package com.lucasdevx.Mentorly.mocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.lucasdevx.Mentorly.dto.response.CertificateResponseDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;
import com.lucasdevx.Mentorly.model.Certificate;
import com.lucasdevx.Mentorly.model.Course;
import com.lucasdevx.Mentorly.model.User;

public class MockCertificate {

	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public MockCertificate() {
	}
	
	public Certificate MockEntity() throws ParseException {
		return mockEntity(0);
	}
	
	public CertificateResponseDTO MockResponseDTO() throws ParseException {
		return mockResponseDTO(0);
	}
	
	public List<Certificate> mockEntityList() throws ParseException{
		List<Certificate> list = new ArrayList<>();
		
		for(int i = 0 ; i < 14 ; i++) {
			list.add(mockEntity(i));
		}
		
		return list;
	}
	
	public List<CertificateResponseDTO> mockResponseDTOList() throws ParseException{
		List<CertificateResponseDTO> list = new ArrayList<>();
		
		for(int i = 0 ; i < 14 ; i++) {
			list.add(mockResponseDTO(i));
		}
		
		return list;
	}
	
	public Certificate mockEntity(Integer number) throws ParseException {
	    Certificate certificate = new Certificate();
		MockUser userMock = new MockUser();
		MockCourse courseMock = new MockCourse();
		User user = userMock.mockEntity(number);
		Course course = courseMock.mockEntity(number);
		
		certificate.setId(number.longValue());
		certificate.setIssueDate(formatter.parse("12/07/2026 10:00"));
		certificate.setUser(user);
		certificate.setCourse(course);
		return certificate;
	}
	
	public CertificateResponseDTO mockResponseDTO(Integer number) throws ParseException {
		MockUser userMock = new MockUser();
		MockCourse courseMock = new MockCourse();
		
		UserResponseDTO userDTO = userMock.mockResponseDTO(number);
		CourseResponseDTO courseDTO = courseMock.mockResponseDTO(number);
		
		return new CertificateResponseDTO(
				number.longValue(), 
				formatter.parse("12/07/2026 10:00"),
				courseDTO,
				userDTO);
	}
}
