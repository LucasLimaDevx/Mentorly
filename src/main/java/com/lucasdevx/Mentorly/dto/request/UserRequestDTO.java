package com.lucasdevx.Mentorly.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public record UserRequestDTO(
	String firstName,
	String lastName,
	String email,
	String password,
	@JsonInclude(content = Include.NON_NULL)
	Boolean active) {
}
