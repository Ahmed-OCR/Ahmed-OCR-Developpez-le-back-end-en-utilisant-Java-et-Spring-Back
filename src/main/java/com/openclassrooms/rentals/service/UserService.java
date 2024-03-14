package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.dto.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	UserResponse findById(int id);
}
