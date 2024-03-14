package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.dto.request.RentalRequest;
import com.openclassrooms.rentals.dto.response.MessageResponse;
import com.openclassrooms.rentals.dto.response.RentalResponse;
import com.openclassrooms.rentals.entity.RentalEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RentalService {

	RentalResponse findAllRentals();

	Optional<RentalEntity> findById(int id);

	void createRental(RentalRequest request);

	ResponseEntity<MessageResponse> updateRental(RentalRequest request, int id);
}
