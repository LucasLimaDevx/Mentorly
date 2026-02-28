package com.lucasdevx.Mentorly.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseRequestDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String title;
	private int workloadHours;
	
	@JsonInclude(content = Include.NON_NULL)
	private Boolean active;
}
