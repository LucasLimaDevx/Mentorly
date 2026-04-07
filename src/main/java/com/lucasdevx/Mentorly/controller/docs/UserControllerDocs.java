package com.lucasdevx.Mentorly.controller.docs;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.lucasdevx.Mentorly.dto.request.UserRequestDTO;
import com.lucasdevx.Mentorly.dto.response.UserResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface UserControllerDocs{
	
	@Operation(
			summary = "Create a User.",
			description = "Create a User.",
			tags = {"User"},
			responses = {
					@ApiResponse(
							responseCode = "201", 
							content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public ResponseEntity<EntityModel<UserResponseDTO>> create(@RequestBody UserRequestDTO request) ;
	
	@Operation(
			summary = "Finds a User.",
			description = "Find a specific User by Id.",
			tags = {"User"},
			responses = {
					@ApiResponse(
							responseCode = "200", 
							content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public ResponseEntity<EntityModel<UserResponseDTO>> findById(@PathVariable Long id);
	
	@Operation(
			summary = "Find All User.",
			description = "Finds All User.",
			tags = {"User"},
			responses = {
					@ApiResponse(
							
							responseCode = "200", 
							content = {
								@Content(
										mediaType = MediaType.APPLICATION_JSON_VALUE,
										array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))),
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
	public ResponseEntity<List<EntityModel<UserResponseDTO>>> findAll();
	
	@Operation(
			summary = "Update a User.",
			description = "Update a information from specific User.",
			tags = {"User"},
			responses = {
					@ApiResponse(
							responseCode = "200", 
							content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public ResponseEntity<EntityModel<UserResponseDTO>> update(@RequestBody UserRequestDTO request, @PathVariable Long id, HttpServletRequest httpRequest);
	
	@Operation(
			summary = "Delete a User.",
			description = "Delete a specific User by their id.",
			tags = {"User"},
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
