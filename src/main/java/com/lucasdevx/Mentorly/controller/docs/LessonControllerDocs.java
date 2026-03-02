package com.lucasdevx.Mentorly.controller.docs;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.lucasdevx.Mentorly.dto.request.LessonRequestDTO;
import com.lucasdevx.Mentorly.dto.response.LessonResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface LessonControllerDocs {
	
	@Operation(
			summary = "Create a Lesson.",
			description = "Create a Lesson.",
			tags = {"Lesson"},
			responses = {
					@ApiResponse(
							responseCode = "201", 
							content = @Content(schema = @Schema(implementation = LessonResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public EntityModel<LessonResponseDTO> create(@RequestBody LessonRequestDTO request) ;
	
	@Operation(
			summary = "Finds a Lesson.",
			description = "Find a specific Lesson by Id.",
			tags = {"Lesson"},
			responses = {
					@ApiResponse(
							responseCode = "200", 
							content = @Content(schema = @Schema(implementation = LessonResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public EntityModel<LessonResponseDTO> findById(@PathVariable Long id);
	
	@Operation(
			summary = "Find All Lesson.",
			description = "Finds All Lesson.",
			tags = {"Lesson"},
			responses = {
					@ApiResponse(
							
							responseCode = "200", 
							content = {
									@Content(
											mediaType = MediaType.APPLICATION_JSON_VALUE,
											array = @ArraySchema(schema = @Schema(implementation = LessonResponseDTO.class))),
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
	public List<EntityModel<LessonResponseDTO>> findAll();
	
	@Operation(
			summary = "Update a Lesson.",
			description = "Update a information from specific Lesson.",
			tags = {"Lesson"},
			responses = {
					@ApiResponse(
							responseCode = "200", 
							content = @Content(schema = @Schema(implementation = LessonResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public EntityModel<LessonResponseDTO> update(@RequestBody LessonRequestDTO request, @PathVariable Long id);
	
	@Operation(
			summary = "Delete a Lesson.",
			description = "Delete a specific Lesson by their id.",
			tags = {"Lesson"},
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
