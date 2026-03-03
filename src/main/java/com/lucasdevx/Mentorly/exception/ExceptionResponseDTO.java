package com.lucasdevx.Mentorly.exception;

import java.util.Date;

public record ExceptionResponseDTO(Date timestamp, String message, String details) {

}
