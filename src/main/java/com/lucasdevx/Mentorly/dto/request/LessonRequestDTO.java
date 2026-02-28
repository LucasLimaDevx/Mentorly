package com.lucasdevx.Mentorly.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LessonRequestDTO  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String title;
	private String description;
	private String videoUrl;
	private int lessonOrder;
}
