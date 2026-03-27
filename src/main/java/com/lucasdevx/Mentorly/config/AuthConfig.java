package com.lucasdevx.Mentorly.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lucasdevx.Mentorly.repository.UserRepository;

@Service
public class AuthConfig implements UserDetailsService {
	
	private final UserRepository userRepository;

	public AuthConfig(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		return userRepository.findUserByEmail(email).get();
	}
	
	
}
