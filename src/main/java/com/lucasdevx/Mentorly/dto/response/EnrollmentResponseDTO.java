package com.lucasdevx.Mentorly.dto.response;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucasdevx.Mentorly.model.Enrollment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentResponseDTO  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date enrollmentDate;
	private int progressPercentage;
	
	public EnrollmentResponseDTO(Enrollment enrollment) {
		this.id = enrollment.getId();
		this.enrollmentDate = enrollment.getEnrollmentDate();
		this.progressPercentage = enrollment.getProgressPercentage();
	}
}
