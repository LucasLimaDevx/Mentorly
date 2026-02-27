package com.lucasdevx.Mentorly.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseRequestDTO {
	
	private String title;
	private int workloadHours;
	
	@JsonInclude(content = Include.NON_NULL)
	private Boolean active;
}
