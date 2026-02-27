package com.lucasdevx.Mentorly.dto.response;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucasdevx.Mentorly.model.Certificate;

public class CertificateResponseDTO  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date issueDate;
	private String validationCode;
	
	public CertificateResponseDTO(Certificate certificate) {
		this.id = certificate.getId();
		this.issueDate = certificate.getIssueDate();
		this.validationCode = certificate.getValidateCode();
	}

}
