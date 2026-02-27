package com.lucasdevx.Mentorly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasdevx.Mentorly.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
