package com.lucasdevx.Mentorly.config;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lucasdevx.Mentorly.model.User;

@Component
public class TokenConfig {
	private String secret = "secret";
	
	public String generateToken(User user) {
		Algorithm algorithm = Algorithm.HMAC256(secret); 
		
		return JWT.create()
				.withClaim("userId", user.getId())
				.withSubject(user.getEmail())
				.withExpiresAt(Instant.now().plusSeconds(86400))
				.withIssuedAt(Instant.now())
				.sign(algorithm);
	}
	
	public Optional<JWTUserData> validateToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		
		try {
			DecodedJWT decodeJWT = JWT.require(algorithm)
					.withIssuer("mentorly")
					.build()
					.verify(token);
			
			
			return Optional.of(JWTUserData.builder()
							.userId(decodeJWT.getClaim("userId").asLong())
							.email(decodeJWT.getSubject())
							.build());
		}
		catch(JWTVerificationException ex) {
			return Optional.empty();
		}
	}
	
}
