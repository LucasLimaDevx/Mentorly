package com.lucasdevx.Mentorly.config;

import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
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
			Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
			
			if(!optUser.isEmpty()) {
				JWTUserData jwtUserData = optUser.get();
				UserDetails user = userRepository.findUserByEmail(jwtUserData.email()).get();

				UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
}
