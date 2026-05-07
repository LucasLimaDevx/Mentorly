package com.lucasdevx.Mentorly.mocks;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.lucasdevx.Mentorly.dto.request.CategoryRequestDTO;
import com.lucasdevx.Mentorly.dto.response.CategoryResponseDTO;
import com.lucasdevx.Mentorly.model.Category;

public class MockCategory {

	public MockCategory() {
	}
	
	public Category MockEntity() throws ParseException {
		return mockEntity(0);
	}
	
	public CategoryRequestDTO mockRequestDTO() throws ParseException {
		return mockRequestDTO(0);
	}
	
	public CategoryResponseDTO MockResponseDTO() throws ParseException {
		return mockResponseDTO(0);
	}
	
	public List<Category> mockEntityList() throws ParseException{
		List<Category> list = new ArrayList<>();
		
		for(int i = 0 ; i < 14 ; i++) {
			list.add(mockEntity(i));
		}
		
		return list;
	}
	
	public List<CategoryResponseDTO> mockResponseDTOList() throws ParseException{
		List<CategoryResponseDTO> list = new ArrayList<>();
		
		for(int i = 0 ; i < 14 ; i++) {
			list.add(mockResponseDTO(i));
		}
		
		return list;
	}
	
	public Category mockEntity(Integer number) throws ParseException {
		Category category = new Category();
		
		category.setId(number.longValue());
		category.setTitle("Title Test" + number);
		category.setDescription("Description Test" + number);
		
		return category;
	}
	
	public CategoryResponseDTO mockResponseDTO(Integer number) throws ParseException {
		
		
		return new CategoryResponseDTO(
				number.longValue(), 
				"Title Test" + number, 
				"Description Test" + number);
	}
	
	public CategoryRequestDTO mockRequestDTO(Integer number) throws ParseException {
		
		return new CategoryRequestDTO(
				"Title Test" + number, 
				"Description Test" + number);
	}
	
}
