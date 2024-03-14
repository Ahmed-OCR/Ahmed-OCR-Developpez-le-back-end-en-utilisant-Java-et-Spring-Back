package com.openclassrooms.rentals.exception;

public class RentalNotFoundException extends RuntimeException {

	public RentalNotFoundException(String message) {
		super(message);
	}
}
