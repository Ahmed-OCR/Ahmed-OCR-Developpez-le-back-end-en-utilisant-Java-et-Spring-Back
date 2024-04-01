package com.openclassrooms.rentals.repository;

import com.openclassrooms.rentals.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository  extends JpaRepository<UserEntity, Integer> {
	Optional<UserEntity> findByEmail(String email);

	Optional<UserEntity> findById(int id);
}
