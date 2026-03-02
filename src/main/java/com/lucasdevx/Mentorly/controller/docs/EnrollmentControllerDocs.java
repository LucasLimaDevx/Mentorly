package com.lucasdevx.Mentorly.controller.docs;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.lucasdevx.Mentorly.dto.request.EnrollmentRequestDTO;
import com.lucasdevx.Mentorly.dto.response.EnrollmentResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface EnrollmentControllerDocs {
	
	@Operation(
			summary = "Create a Enrollment.",
			description = "Create a Enrollment.",
			tags = {"Enrollment"},
			responses = {
					@ApiResponse(
							responseCode = "201", 
							content = @Content(schema = @Schema(implementation = EnrollmentResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public ResponseEntity<EntityModel<EnrollmentResponseDTO>> create(@RequestBody EnrollmentRequestDTO request) ;
	
	@Operation(
			summary = "Finds a Enrollment.",
			description = "Find a specific Enrollment by Id.",
			tags = {"Enrollment"},
			responses = {
					@ApiResponse(
							responseCode = "200", 
							content = @Content(schema = @Schema(implementation = EnrollmentResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public ResponseEntity<EntityModel<EnrollmentResponseDTO>> findById(@PathVariable Long id);
	
	@Operation(
			summary = "Find All Enrollment.",
			description = "Finds All Enrollment.",
			tags = {"Enrollment"},
			responses = {
					@ApiResponse(
							
							responseCode = "200", 
							content = { 
									@Content(
											mediaType = MediaType.APPLICATION_JSON_VALUE,
											array = @ArraySchema(schema = @Schema(implementation = EnrollmentResponseDTO.class))),
									@Content(mediaType = MediaType.APPLICATION_XML_VALUE),
									@Content(mediaType = MediaType.APPLICATION_YAML_VALUE)
							}
							
					),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public ResponseEntity<List<EntityModel<EnrollmentResponseDTO>> > findAll();
	
	@Operation(
			summary = "Update a Enrollment.",
			description = "Update a information from specific Enrollment.",
			tags = {"Enrollment"},
			responses = {
					@ApiResponse(
							responseCode = "200", 
							content = @Content(schema = @Schema(implementation = EnrollmentResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public ResponseEntity<EntityModel<EnrollmentResponseDTO>> update(@RequestBody EnrollmentRequestDTO request, @PathVariable Long id);
	
	@Operation(
			summary = "Delete a Enrollment.",
			description = "Delete a specific Enrollment by their id.",
			tags = {"Enrollment"},
			responses = {
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public ResponseEntity<Void> delete(@PathVariable Long id);;
}
