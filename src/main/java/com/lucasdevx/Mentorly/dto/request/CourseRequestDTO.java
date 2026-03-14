package com.lucasdevx.Mentorly.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public record CourseRequestDTO(
	String title,
	int workloadHours,
	@JsonInclude(content = Include.NON_NULL)
	Boolean active,
	String courseLevel){}
