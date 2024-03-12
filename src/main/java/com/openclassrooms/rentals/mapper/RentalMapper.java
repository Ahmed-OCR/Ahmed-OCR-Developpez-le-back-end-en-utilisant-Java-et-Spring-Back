package com.openclassrooms.rentals.mapper;

import com.openclassrooms.rentals.dto.request.RentalRequest;
import com.openclassrooms.rentals.entity.RentalEntity;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {

	public static RentalEntity mapToRental(RentalRequest rentalRequest) {
		RentalEntity rental = new RentalEntity();
		rental.setName(rentalRequest.getName());
		rental.setSurface(rentalRequest.getSurface());
		rental.setPrice(rentalRequest.getPrice());
		rental.setPicture(rentalRequest.getPicture());
		rental.setDescription(rentalRequest.getDescription());
		return rental;
	}

	public static RentalRequest mapToRentalRequest(RentalEntity rental) {
		RentalRequest request = new RentalRequest();
		request.setName(rental.getName());
		request.setSurface(rental.getSurface());
		request.setPrice(rental.getPrice());
		request.setPicture(rental.getPicture());
		request.setDescription(rental.getDescription());
		return request;
	}
}
