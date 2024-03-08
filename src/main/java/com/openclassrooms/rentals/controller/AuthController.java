package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.request.UserRequest;
import com.openclassrooms.rentals.exception.UserCreationException;
import com.openclassrooms.rentals.service.JWTService;
import com.openclassrooms.rentals.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {


	private final JWTService jwtService;

	private final UserServiceImpl userService;

	public AuthController(JWTService jwtService, UserServiceImpl userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@PostMapping("/login")
	public String getToken(Authentication authentication) {
		String token = jwtService.generateToken(authentication);
		return token;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserRequest request) {
		UserRequest user = new UserRequest(request.getEmail(), request.getName(), request.getPassword());
		this.userService.createUser(user);

		return ResponseEntity.status(HttpStatus.CREATED).body("Utilisateur créé avec succès");
	}

	@ExceptionHandler(UserCreationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleUserCreationException(UserCreationException ex) {
		return ex.getMessage();
	}
}