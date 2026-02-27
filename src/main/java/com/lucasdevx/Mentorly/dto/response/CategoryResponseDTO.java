package com.lucasdevx.Mentorly.dto.response;

import java.io.Serializable;

import com.lucasdevx.Mentorly.model.Category;

public class CategoryResponseDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private String description;
	
	public CategoryResponseDTO(Category category) {
		this.id = category.getId();
		this.title = category.getTitle();
		this.description = category.getDescription();
	}
}
