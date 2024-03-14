package com.openclassrooms.rentals.service.impl;

import com.openclassrooms.rentals.dto.request.RentalRequest;
import com.openclassrooms.rentals.dto.response.MessageResponse;
import com.openclassrooms.rentals.dto.response.RentalResponse;
import com.openclassrooms.rentals.entity.RentalEntity;
import com.openclassrooms.rentals.exception.RentalNotFoundException;
import com.openclassrooms.rentals.mapper.RentalMapper;
import com.openclassrooms.rentals.repository.RentalRepository;
import com.openclassrooms.rentals.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService {

	private final RentalRepository rentalRepository;

	@Override
	public RentalResponse findAllRentals() {
		List<RentalEntity> rentals = this.rentalRepository.findAll();
		return new RentalResponse(rentals);
	}

	@Override
	public Optional<RentalEntity> findById(int id) {
		Optional<RentalEntity> rental = this.rentalRepository.findById(id);
		if (rental.isEmpty()) {
			throw new RentalNotFoundException("Location introuvable avec l'ID : " + id);
		}
		return rental;
	}

	@Override
	public ResponseEntity<MessageResponse> createRental(RentalRequest request) {
		RentalEntity rental = RentalMapper.mapToRental(request);
		try {
			this.rentalRepository.save(rental);
			MessageResponse message = MessageResponse.builder()
					.message("Rental created !")
					.build();
			return ResponseEntity.status(HttpStatus.CREATED).body(message);
		} catch (Exception e) {
			MessageResponse errorResponse = MessageResponse.builder()
					.message("An error occurred while creating the rental.")
					.build();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
	}

	@Override
	public ResponseEntity<MessageResponse> updateRental(RentalRequest request, int id) {
		Optional<RentalEntity> optionalRental = rentalRepository.findById(id);
		if (optionalRental.isPresent()) {
			RentalEntity existingRental = optionalRental.get();
			existingRental.setName(request.getName());
			existingRental.setSurface(request.getSurface());
			existingRental.setPrice(request.getPrice());
			existingRental.setDescription(request.getDescription());
			this.rentalRepository.save(existingRental);
			MessageResponse message = MessageResponse.builder()
					.message("Rental updated !")
					.build();
			return ResponseEntity.status(HttpStatus.CREATED).body(message);
		} else {
			MessageResponse errorResponse = MessageResponse.builder()
					.message("An error occurred while updating the rental.")
					.build();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
	}
}

