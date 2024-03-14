package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.request.RentalRequest;
import com.openclassrooms.rentals.dto.response.MessageResponse;
import com.openclassrooms.rentals.dto.response.RentalResponse;
import com.openclassrooms.rentals.entity.RentalEntity;
import com.openclassrooms.rentals.service.RentalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Tag(name = "Locations")
@RequestMapping(value = "/rentals", produces = MediaType.APPLICATION_JSON_VALUE)
public class RentalController {

	private final RentalService rentalService;

	@Autowired
	public RentalController(RentalService rentalService) {
		this.rentalService = rentalService;
	}

	@GetMapping
	public RentalResponse findAllRentals() {
		return this.rentalService.findAllRentals();
	}

	@GetMapping("/{id}")
	public Optional<RentalEntity> findAllRentals(@PathVariable int id) {
		return this.rentalService.findById(id);
	}

	@PostMapping
	public ResponseEntity<MessageResponse> createRental(@RequestBody RentalRequest request) {
		try {
			this.rentalService.createRental(request);
			MessageResponse message = MessageResponse.builder()
					.message("Rental created !")
					.build();
			return ResponseEntity.status(HttpStatus.CREATED).body(message);
		} catch (Exception e) {
			MessageResponse errorResponse = MessageResponse.builder()
					.message("An error occurred while creating the rental.")
					.build();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> updateRental(@RequestBody RentalRequest request, @PathVariable int id) {
			return this.rentalService.updateRental(request, id);

	}
}
