package com.lucasdevx.Mentorly.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.lucasdevx.Mentorly.exception.ExceptionResponseDTO;
import com.lucasdevx.Mentorly.exception.ObjectNotFoundException;

@ControllerAdvice
public class GlobalHandlerException {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<ExceptionResponseDTO> objectNotFoundException(ObjectNotFoundException exception, WebRequest request) {
		ExceptionResponseDTO response = new ExceptionResponseDTO(new Date(), exception.getMessage(), request.getDescription(false));
	
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponseDTO> hanldeAllException(Exception exception, WebRequest request) {
		ExceptionResponseDTO response = new ExceptionResponseDTO(new Date(), exception.getMessage(), request.getDescription(false));
	
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
