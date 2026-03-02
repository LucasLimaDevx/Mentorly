package com.lucasdevx.Mentorly.controller.docs;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.lucasdevx.Mentorly.dto.response.CertificateResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface CertificateControllerDocs {
	
	@Operation(
			summary = "Finds a Certificate.",
			description = "Find a specific Certificate by Id.",
			tags = {"Certificate"},
			responses = {
					@ApiResponse(
							responseCode = "200", 
							content = @Content(schema = @Schema(implementation = CertificateResponseDTO.class))),
					@ApiResponse(responseCode = "204", content = @Content),
					@ApiResponse(responseCode = "400", content = @Content),
					@ApiResponse(responseCode = "401", content = @Content),
					@ApiResponse(responseCode = "404", content = @Content),
					@ApiResponse(responseCode = "500", content = @Content)
			}
			
	)
	public ResponseEntity<EntityModel<CertificateResponseDTO>> findById(@PathVariable Long id);
	
	@Operation(
			summary = "Find All Certificate.",
			description = "Finds All Certificate.",
			tags = {"Certificate"},
			responses = {
					@ApiResponse(
							
							responseCode = "200", 
							content = {
								@Content(
										mediaType = MediaType.APPLICATION_JSON_VALUE,
										array = @ArraySchema(schema = @Schema(implementation = CertificateResponseDTO.class))),
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
	public ResponseEntity<List<EntityModel<CertificateResponseDTO>>> findAll();
	
	@Operation(
			summary = "Delete a Certificate.",
			description = "Delete a specific Certificate by their id.",
			tags = {"Certificate"},
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
