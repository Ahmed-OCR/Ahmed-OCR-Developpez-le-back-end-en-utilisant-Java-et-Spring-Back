package com.openclassrooms.rentals.exception;

public class JwtException extends Throwable {
	public String message;
	public JwtException(String message) {
		super(message);
	}
}


