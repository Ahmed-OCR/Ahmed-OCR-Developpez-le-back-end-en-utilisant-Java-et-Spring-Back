package com.openclassrooms.rentals.service.impl;

import com.openclassrooms.rentals.dto.request.UserRequest;
import com.openclassrooms.rentals.dto.response.UserResponse;
import com.openclassrooms.rentals.entity.UserEntity;
import com.openclassrooms.rentals.exception.UserCreationException;
import com.openclassrooms.rentals.mapper.UserMapper;
import com.openclassrooms.rentals.repository.UserRepository;
import com.openclassrooms.rentals.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserEntity createUser(UserRequest userRequest) {
		validateUserRequest(userRequest);

		UserEntity user = new UserEntity();
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setName(userRequest.getName());
		user.setEmail(userRequest.getEmail());

		return userRepository.save(user);
	}

	private void validateUserRequest(UserRequest userRequest) {
		if (userRequest.getEmail() == null || userRequest.getEmail().isEmpty()) {
			throw new UserCreationException("L'email ne peut pas être vide");
		}
		if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
			throw new UserCreationException("Un utilisateur existe déjà avec cet email ");
		}
		if (userRequest.getPassword() == null || userRequest.getPassword().isEmpty()) {
			throw new UserCreationException("Le mot de passe ne peut pas être vide");
		}
	}


	@Override
	public UserResponse findById(int id) {
//		return UserMapper.UserEntitytoUserResponse(this.userRepository.findById(id));
		Optional<UserEntity> userOptional = this.userRepository.findById(id);
		if (userOptional.isPresent()) {
			return UserMapper.UserEntitytoUserResponse(userOptional.get());
		} else {
			return new UserResponse();
//			throw new UserNotFoundException("Utilisateur introuvable avec l'ID : " + id);
		}
	}
}
