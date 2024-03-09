package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.request.LoginRequest;
import com.openclassrooms.rentals.dto.request.UserRequest;
import com.openclassrooms.rentals.exception.UserCreationException;
import com.openclassrooms.rentals.service.JWTService;
import com.openclassrooms.rentals.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {


	private final JWTService jwtService;
	private final UserServiceImpl userService;
	private final AuthenticationManager authenticationManager;

	public AuthController(JWTService jwtService, UserServiceImpl userService, AuthenticationManager authenticationManager) {
		this.jwtService = jwtService;
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<String> getToken(@RequestBody LoginRequest loginRequest) {
		try {
			if (loginRequest.getLogin() == null || loginRequest.getPassword() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email et/ou password sont obligatoires");
			}
			// Créer un jeton d'authentification avec l'email et le mot de passe fournis
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword());
			// Authentifier l'utilisateur en utilisant AuthenticationManager
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Génération du token JWT
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String token = jwtService.generateToken(userDetails);

			// Retourner le token dans une réponse HTTP
			return ResponseEntity.ok(token);
		} catch (AuthenticationException e) {
			System.out.println("L'authentification a echouée");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("L'authentification a echouée");
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