package com.openclassrooms.rentals.filter;

import com.openclassrooms.rentals.config.CustomUserDetailsService;
import com.openclassrooms.rentals.exception.JwtException;
import com.openclassrooms.rentals.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Service
public class JwtFilter extends OncePerRequestFilter {
	private final CustomUserDetailsService userService;
	private final JwtParser jwtParser;
	private final JwtService jwtService;

	public JwtFilter(CustomUserDetailsService userService, JwtParser jwtParser, JwtService jwtService) {
		this.userService = userService;
		this.jwtParser = jwtParser;
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = extractTokenFromHeader(request);

		if (token != null) {
			try {
				Claims claims = validateToken(token);
				String username = extractUsernameFromClaims(claims);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = loadUserByUsername(username);
					authenticateUser(userDetails);
				}
			} catch (JwtException e) {
				handleJwtException(e, response);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	private void handleJwtException(JwtException e, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	private String extractTokenFromHeader(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.startsWith("Bearer ")) {
			return authorization.substring(7);
		}
		return null;
	}

	private Claims validateToken(String token) throws JwtException {
		if (!isTokenValid(token)) {
			throw new JwtException("JWT Token est invalide ou expire 2");
		}
		return jwtParser.parseClaimsJws(token).getBody();
	}

	private boolean isTokenValid(String token) throws ExpiredJwtException {
		return this.jwtService.isTokenValid(token);
	}

	private String extractUsernameFromClaims(Claims claims) {
		return claims.getSubject();
	}

	private UserDetails loadUserByUsername(String username) {
		return userService.loadUserByUsername(username);
	}

	private void authenticateUser(UserDetails userDetails) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
}
