package com.lucasdevx.Mentorly.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnrollmentRequestDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int progressPercentage;
}
