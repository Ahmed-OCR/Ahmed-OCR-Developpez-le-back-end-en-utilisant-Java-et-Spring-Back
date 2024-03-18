package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.request.AuthentificationDTO;
import com.openclassrooms.rentals.dto.request.UserRequest;
import com.openclassrooms.rentals.dto.response.LoginResponse;
import com.openclassrooms.rentals.dto.response.MessageResponse;
import com.openclassrooms.rentals.dto.response.UserResponse;
import com.openclassrooms.rentals.exception.UserCreationException;
import com.openclassrooms.rentals.service.JwtService;
import com.openclassrooms.rentals.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@AllArgsConstructor
@Tag(name = "Authentification")
@RequestMapping(value = "/auth")
public class AuthController {
	private AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserServiceImpl userService;

//	@PostMapping("/login")
//	public ResponseEntity<LoginResponse> getToken(@RequestBody UserRequest request, AuthenticationManager authenticationManager) {
//		//401 si non authentification
//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//
//		try {
//			LoginResponse token =
//					LoginResponse.builder()
//									.token(jwtService.generateToken(authentication))
//					             	.build();
//			return ResponseEntity.ok(token);
//		} catch (AuthenticationException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//	}

	@GetMapping("/me")
	public UserResponse getMe(@RequestHeader("Authorization") String authorizationHeader) {
		return this.userService.getMe(authorizationHeader);
	}

	@PostMapping("/login")
	public ResponseEntity<?> getToken(@RequestBody AuthentificationDTO authentificationDTO) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentificationDTO.email(), authentificationDTO.password()));
			LoginResponse token =
					LoginResponse.builder()
							.token(jwtService.generateToken(authentication))
							.build();
			return ResponseEntity.ok(token);
		} catch (AuthenticationException e) {
			MessageResponse message =
					MessageResponse.builder()
							.message("error")
							.build();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRequest request) {
		try {
			this.userService.createUser(request);
			LoginResponse token =
					LoginResponse.builder()
							.token(jwtService.generateToken(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())))
							.build();
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyMap());
		}

	}

	@ExceptionHandler(UserCreationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleUserCreationException(UserCreationException ex) {
		return ex.getMessage();
	}
}