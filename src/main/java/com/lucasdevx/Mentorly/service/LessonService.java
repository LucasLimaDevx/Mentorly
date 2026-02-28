package com.lucasdevx.Mentorly.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.dto.request.LessonRequestDTO;
import com.lucasdevx.Mentorly.dto.response.LessonResponseDTO;
import com.lucasdevx.Mentorly.mapper.LessonMapper;
import com.lucasdevx.Mentorly.model.Lesson;
import com.lucasdevx.Mentorly.repository.LessonRepository;

@Service
public class LessonService {

	private LessonRepository lessonRepository;
	private LessonMapper lessonMapper;
	
	public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper) {
		this.lessonRepository = lessonRepository;
		this.lessonMapper = lessonMapper;
	}
	
	public LessonResponseDTO create(LessonRequestDTO request) {
		
		Lesson lesson = lessonMapper.converterToEntity(request);
		Lesson lessonPersisted = lessonRepository.save(lesson);
		
		LessonResponseDTO response = lessonMapper.converterToDto(lessonPersisted);
		
		return response;
	}
	
	public LessonResponseDTO findById(Long id) {
		Lesson lessonPersisted = lessonRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		
		LessonResponseDTO response = lessonMapper.converterToDto(lessonPersisted);
		
		return response;
	}
	
	public List<LessonResponseDTO> findAll() {
		List<Lesson> lessonsPersisted = lessonRepository.findAll();
		
		List<LessonResponseDTO> responsesDTO = lessonsPersisted.stream()
				.map((response) -> lessonMapper.converterToDto(response))
				.toList();
		
		return responsesDTO;
	}
	
	public LessonResponseDTO update(LessonRequestDTO request ,Long id) {
		Lesson lessonPersisted = lessonRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("Invalid ID"));
		
		Lesson lessonUpdated = updateDate(lessonPersisted, request);
		
		LessonResponseDTO response = lessonMapper.converterToDto(lessonRepository.save(lessonUpdated));
		
		return response;
	}
	
	public void delete(Long id) {
		/*Lesson lessonPersisted = lessonRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
		*/
		lessonRepository.deleteById(id);
	}
	
	public Lesson updateDate(Lesson lesson, LessonRequestDTO request) {
		
		lesson.setTitle(request.getTitle());
		lesson.setDescription(request.getDescription());
		lesson.setVideoUrl(request.getVideoUrl());
		lesson.setLessonOrder(request.getLessonOrder());
		
		return lesson;
	}
}
