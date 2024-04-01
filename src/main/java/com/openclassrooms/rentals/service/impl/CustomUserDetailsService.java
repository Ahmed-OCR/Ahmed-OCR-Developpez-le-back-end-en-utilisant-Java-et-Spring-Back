package com.openclassrooms.rentals.service.impl;

import com.openclassrooms.rentals.entity.UserEntity;
import com.openclassrooms.rentals.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserEntity> userOptional = userRepository.findByEmail(email);
		if (userOptional.isEmpty()) {
			throw new UsernameNotFoundException("User non trouvé: " + email);
		}
		UserEntity user = userOptional.get();

		return User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.build();
	}

}