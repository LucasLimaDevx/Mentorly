package com.lucasdevx.Mentorly.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.lucasdevx.Mentorly.model.enums.Level;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	@Column(name = "course_level", nullable = false)
	@Enumerated(EnumType.STRING)
	private Level level;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable =  false)
	private Category category;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Enrollment> enrollments = new HashSet<>();
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Lesson> lessons = new HashSet<>();
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Certificate> certificates = new HashSet<>();
}
