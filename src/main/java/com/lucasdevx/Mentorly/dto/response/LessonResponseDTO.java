package com.lucasdevx.Mentorly.dto.response;

import java.io.Serializable;

import com.lucasdevx.Mentorly.model.Lesson;

public class LessonResponseDTO  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private String description;
	private String videoUrl;
	private int lessonOrder;
	
	public LessonResponseDTO(Lesson lesson) {
		this.id = lesson.getId();
		this.title = lesson.getTitle();
		this.description = lesson.getDescription();
		this.videoUrl = lesson.getVideoUrl();
		this.lessonOrder = lesson.getLessonOrder();
		
	}

}
