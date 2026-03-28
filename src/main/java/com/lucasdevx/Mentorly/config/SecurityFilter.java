package com.lucasdevx.Mentorly.config;

import java.io.IOException;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lucasdevx.Mentorly.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	private final UserRepository userRepository;
	private final TokenConfig tokenConfig;
	private final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
	
	public SecurityFilter(UserRepository userRepository, TokenConfig tokenConfig) {
		this.userRepository = userRepository;
		this.tokenConfig = tokenConfig;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorizeHeader = request.getHeader("Authorization");
		
		if(Strings.isNotEmpty(authorizeHeader) && authorizeHeader.startsWith("Bearer ")) {
			String token = authorizeHeader.replace("Bearer ", "");
			String email = tokenConfig.validateToken(token);
			UserDetails user = userRepository.findUserByEmail(email).get();
			
			UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordToken);
		}
		
		filterChain.doFilter(request, response);
	}
	
}
