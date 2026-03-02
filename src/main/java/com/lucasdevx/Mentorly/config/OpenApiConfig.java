package com.lucasdevx.Mentorly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI customOpenApi() {
		
		return new OpenAPI()
				.info(new Info()
						.title("Mentorly API")
						.description(
								"Mentorly API é uma aplicação backend RESTful desenvolvida com " +
								"Spring Boot que simula uma plataforma completa de cursos online " +
								"(Learning Management System - LMS).")
						.version("1.0")
						.license(new License()
								.name("Apache 2.0")));
	}
}
