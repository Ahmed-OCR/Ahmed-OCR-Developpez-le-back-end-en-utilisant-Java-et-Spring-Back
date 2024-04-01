package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class JwtService {

	private JwtTokenUtil jwtTokenUtil;

	public String generateToken(Authentication authentication) {
		return this.jwtTokenUtil.generateToken(authentication);
	}

	public boolean isTokenValid(String token) {
		return this.jwtTokenUtil.isTokenValid(token);
	}

	public String getUsernameFromToken(String token) { return this.jwtTokenUtil.getUsernameFromToken(token); }
}