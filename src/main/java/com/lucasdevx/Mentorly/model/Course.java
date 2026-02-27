package com.lucasdevx.Mentorly.model;

import java.util.Date;

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
@Table(name = "tb_courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "course_title", nullable = false, length = 255)
	private String title;
	
	@Column(name = "course_workloadHours", nullable = false, length = 255)
	private int workloadHours;
	
	@Column(name = "course_active")
	private boolean active;
	
	@Column(nullable = false)
	private Date created;
}
