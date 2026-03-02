package com.lucasdevx.Mentorly.config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.http.converter.yaml.JacksonYamlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tools.jackson.dataformat.yaml.YAMLMapper;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configure) {
		configure
			.favorParameter(false)
			.ignoreAcceptHeader(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML)
				.mediaType("json", MediaType.APPLICATION_JSON)
				.mediaType("yaml", MediaType.APPLICATION_YAML);
	}
	
	@Override
	public void configureMessageConverters(HttpMessageConverters.ServerBuilder builder) {
		YAMLMapper yamlMapper = YAMLMapper.builder().findAndAddModules()
				.defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
				.build();
		
		builder.withYamlConverter(new JacksonYamlHttpMessageConverter(yamlMapper));
	}
}
