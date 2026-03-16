package com.lucasdevx.Mentorly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasdevx.Mentorly.model.Role;
import com.lucasdevx.Mentorly.model.enums.RoleEnum;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long>{
	Role findByRole(RoleEnum role);
}
