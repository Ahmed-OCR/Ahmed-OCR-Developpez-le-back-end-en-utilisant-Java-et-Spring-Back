package com.openclassrooms.rentals.service.impl;

import com.openclassrooms.rentals.dto.request.UserRequest;
import com.openclassrooms.rentals.entity.User;
import com.openclassrooms.rentals.exception.UserCreationException;
import com.openclassrooms.rentals.repository.UserRepository;
import com.openclassrooms.rentals.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createUser(UserRequest userRequest) {
		validateUserRequest(userRequest);

		User user = new User();
		user.setPassword(userRequest.getPassword());
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
}
