package com.lucasdevx.Mentorly.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Enrollment  implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "enrollment_date", nullable = false)
	private Date enrollmentDate;
	
	@Column(name = "enrollment_progressPercentage", length = 3)
	private int progressPercentage;
	
	
}
