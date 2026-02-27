package com.lucasdevx.Mentorly.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LessonRequestDTO {
	
	private String title;
	private String description;
	private String videoUrl;
	private int lessonOrder;
}
