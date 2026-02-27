package com.lucasdevx.Mentorly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasdevx.Mentorly.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
