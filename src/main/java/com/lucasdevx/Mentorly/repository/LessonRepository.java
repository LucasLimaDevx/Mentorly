package com.lucasdevx.Mentorly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasdevx.Mentorly.model.Lesson;

@Repository
public interface LessonRepository  extends JpaRepository<Lesson, Long>{

}
