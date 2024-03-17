package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.dto.request.RentalRequest;
import com.openclassrooms.rentals.dto.response.MessageResponse;
import com.openclassrooms.rentals.dto.response.RentalsResponse;
import com.openclassrooms.rentals.entity.RentalEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public interface RentalService {

	RentalsResponse findAllRentals();

	Optional<RentalEntity> findById(int id);

	ResponseEntity<MessageResponse> createRental(int id,MultipartFile picture,RentalRequest request);

	ResponseEntity<MessageResponse> updateRental(RentalRequest request, int id);
}
