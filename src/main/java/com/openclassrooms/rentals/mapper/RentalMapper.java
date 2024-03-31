package com.openclassrooms.rentals.mapper;

import com.openclassrooms.rentals.dto.request.RentalRequest;
import com.openclassrooms.rentals.dto.response.RentalResponse;
import com.openclassrooms.rentals.entity.RentalEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentalMapper {

	public static RentalEntity mapToRental(int id, RentalRequest rentalRequest) {
		RentalEntity rental = new RentalEntity();
		rental.setName(rentalRequest.getName());
		rental.setSurface(rentalRequest.getSurface());
		rental.setPrice(rentalRequest.getPrice());
		rental.setDescription(rentalRequest.getDescription());
		rental.setOwner_id(id);
		return rental;
	}

	public static List<RentalResponse> toRentalResponse(List<RentalEntity> rentalsEntity) {
		List<RentalResponse> rentalResponses = new ArrayList<>();
		for (RentalEntity rentals : rentalsEntity) {
			rentalResponses.add(toRentalResponseSetter(rentals));
		}
		return rentalResponses;
	}

	public static RentalResponse toRentalResponseSetter(RentalEntity rentals) {
		RentalResponse rentalResponse = new RentalResponse();
		rentalResponse.setId(rentals.getId());
		rentalResponse.setName(rentals.getName());
		rentalResponse.setSurface(rentals.getSurface());
		rentalResponse.setPrice(rentals.getPrice());
		rentalResponse.setPicture(rentals.getPicture());
		rentalResponse.setDescription(rentals.getDescription());
		rentalResponse.setOwner_id(rentals.getOwner_id());
		rentalResponse.setCreated_at(rentals.getCreated_at());
		if (rentals.getUpdated_at() != null) {
			rentalResponse.setUpdated_at(rentals.getUpdated_at());
		}
		return rentalResponse;
	}
}
