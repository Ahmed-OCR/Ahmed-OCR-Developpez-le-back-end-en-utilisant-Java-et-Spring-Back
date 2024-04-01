package com.openclassrooms.rentals.exception.rentals;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RentalNotFoundException extends RuntimeException {
	public RentalNotFoundException() {}
}
