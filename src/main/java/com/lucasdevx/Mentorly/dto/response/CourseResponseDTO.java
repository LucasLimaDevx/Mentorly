package com.lucasdevx.Mentorly.dto.response;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucasdevx.Mentorly.model.Course;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponseDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date created;
	private int workloadHours;
	private boolean active;
	
	public CourseResponseDTO(Course course) {
		this.id = course.getId();
		this.title = course.getTitle();
		this.workloadHours = course.getWorkloadHours();
		this.created = course.getCreated();
		this.active = course.isActive();
	}
	
	
}
