package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.dto.request.UserRequest;
import com.openclassrooms.rentals.dto.response.UserResponse;

public interface UserService {
	UserResponse findById(int id);
	UserResponse getMe(String token);
	void createUser(UserRequest userRequest);
}
