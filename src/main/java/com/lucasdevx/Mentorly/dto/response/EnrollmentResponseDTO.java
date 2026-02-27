package com.lucasdevx.Mentorly.dto.response;

import java.io.Serializable;
import java.util.Date;

import com.lucasdevx.Mentorly.model.Enrollment;

public class EnrollmentResponseDTO  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date enrollmentDate;
	private int progressPercentage;
	
	public EnrollmentResponseDTO(Enrollment enrollment) {
		this.id = enrollment.getId();
		this.enrollmentDate = enrollment.getEnrollmentDate();
		this.progressPercentage = enrollment.getProgressPercentage();
	}
}
