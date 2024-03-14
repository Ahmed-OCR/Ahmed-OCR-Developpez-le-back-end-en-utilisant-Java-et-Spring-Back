package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.response.UserResponse;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Users")
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}


	@GetMapping("/{id}")
	public UserResponse findAllUsers(@PathVariable int id) {
		return this.userService.findById(id);
	}
}
