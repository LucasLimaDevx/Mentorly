package com.lucasdevx.Mentorly.controller.docs;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.lucasdevx.Mentorly.dto.request.CourseRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CourseResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface CourseControllerDocs {
	
	@Operation(
			summary = "Create a Course.",
			description = "Create a Course.",
			tags = {"Course"},
			responses = {
					@ApiResponse(
							responseCode = "201", 
							content = @Content(schema = @Schema(implementation = CourseResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public ResponseEntity<EntityModel<CourseResponseDTO>> create(@RequestBody CourseRequestDTO request) ;
	
	@Operation(
			summary = "Finds a Course.",
			description = "Find a specific Course by Id.",
			tags = {"Course"},
			responses = {
					@ApiResponse(
							responseCode = "200", 
							content = @Content(schema = @Schema(implementation = CourseResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public ResponseEntity<EntityModel<CourseResponseDTO>> findById(@PathVariable Long id);
	
	@Operation(
			summary = "Find All Course.",
			description = "Finds All Course.",
			tags = {"Course"},
			responses = {
					@ApiResponse(
							
							responseCode = "200", 
							content = {
								@Content(
										mediaType = MediaType.APPLICATION_JSON_VALUE,
										array = @ArraySchema(schema = @Schema(implementation = CourseResponseDTO.class))),
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
	public ResponseEntity<List<EntityModel<CourseResponseDTO>> > findAll();
	
	@Operation(
			summary = "Update a Course.",
			description = "Update a information from specific Course.",
			tags = {"Course"},
			responses = {
					@ApiResponse(
							responseCode = "200", 
							content = @Content(schema = @Schema(implementation = CourseResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public ResponseEntity<EntityModel<CourseResponseDTO>> update(@RequestBody CourseRequestDTO request, @PathVariable Long id);
	
	@Operation(
			summary = "Delete a Course.",
			description = "Delete a specific Course by their id.",
			tags = {"Course"},
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
