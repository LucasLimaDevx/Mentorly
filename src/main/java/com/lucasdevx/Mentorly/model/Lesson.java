package com.lucasdevx.Mentorly.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_lessons")
public class Lesson{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "lesson_title", nullable = false, length = 255)
	private String title;
	
	@Column(name = "lesson_description", nullable = false, length = 255)
	private String description;
	
	@Column(name = "lesson_videoUrl", nullable = false, length = 500)
	private String videoUrl;
	
	@Column(name = "lesson_Order")
	private int lessonOrder;
	
}
