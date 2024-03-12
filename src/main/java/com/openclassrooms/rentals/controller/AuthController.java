package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.request.UserRequest;
import com.openclassrooms.rentals.dto.response.TokenResponse;
import com.openclassrooms.rentals.exception.UserCreationException;
import com.openclassrooms.rentals.service.JwtService;
import com.openclassrooms.rentals.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Authentification")
@RequestMapping(value = "/auth")
public class AuthController {


	private final JwtService jwtService;
	private final UserServiceImpl userService;

	public AuthController(JwtService jwtService, UserServiceImpl userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> getToken(Authentication authentication) {
		try {
			TokenResponse token =
					TokenResponse.builder()
									.token(jwtService.generateToken(authentication))
					             	.build();
			return ResponseEntity.ok(token);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserRequest request) {
		UserRequest user = UserRequest.builder()
										.email(request.getEmail())
										.name(request.getName())
										.password(request.getPassword())
										.build();

		this.userService.createUser(user);

		return ResponseEntity.status(HttpStatus.CREATED).body("Utilisateur créé avec succès");
	}

	@ExceptionHandler(UserCreationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleUserCreationException(UserCreationException ex) {
		return ex.getMessage();
	}
}