package com.openclassrooms.rentals.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserCreationException extends RuntimeException {

	public UserCreationException() {

	}
}