package com.openclassrooms.rentals.service.impl;

import com.openclassrooms.rentals.dto.request.UserRequest;
import com.openclassrooms.rentals.dto.response.UserResponse;
import com.openclassrooms.rentals.entity.UserEntity;
import com.openclassrooms.rentals.mapper.UserMapper;
import com.openclassrooms.rentals.repository.UserRepository;
import com.openclassrooms.rentals.service.JwtService;
import com.openclassrooms.rentals.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@Override
	public UserResponse getMe(String authorizationHeader) {
		String token = extractTokenFromHeader(authorizationHeader);
		String name = this.jwtService.getUsernameFromToken(token);
		Optional<UserEntity> userEntity = this.userRepository.findByEmail(name);
		return userEntity.map(UserMapper::UserEntitytoUserResponse).orElseGet(UserResponse::new);
	}

	@Override
	public void createUser(UserRequest userRequest) {
		try {
			validateUserRequest(userRequest);

			UserEntity user = new UserEntity();
			user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
			user.setName(userRequest.getName());
			user.setEmail(userRequest.getEmail());

			this.userRepository.save(user);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public UserResponse findById(int id) {
		Optional<UserEntity> userOptional = this.userRepository.findById(id);
		if (userOptional.isPresent()) {
			return UserMapper.UserEntitytoUserResponse(userOptional.get());
		} else {
			return new UserResponse();
		}
	}

	private String extractTokenFromHeader(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7); // Supprime le préfixe "Bearer " pour obtenir le token uniquement
		}
		return null;
	}

	private void validateUserRequest(UserRequest userRequest) {

		if (
			userRequest.getEmail() == null || userRequest.getEmail().isEmpty() ||			//Le mail est requis.
			userRequest.getName() == null || userRequest.getName().isEmpty() || 			//Le nom est requis.
			userRequest.getPassword() == null || userRequest.getPassword().isEmpty() ||		//Le mot de passe est requis.
			userRepository.findByEmail(userRequest.getEmail()).isPresent()					//Un utilisateur avec cet e-mail existe déjà.
		) {
			throw new IllegalArgumentException();
		}
	}


}
