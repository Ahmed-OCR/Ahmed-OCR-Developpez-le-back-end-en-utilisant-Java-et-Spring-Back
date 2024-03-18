package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.request.RentalRequest;
import com.openclassrooms.rentals.dto.response.MessageResponse;
import com.openclassrooms.rentals.dto.response.RentalsResponse;
import com.openclassrooms.rentals.dto.response.UserResponse;
import com.openclassrooms.rentals.entity.RentalEntity;
import com.openclassrooms.rentals.service.RentalService;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "Locations")
@RequestMapping(value = "/rentals", produces = MediaType.APPLICATION_JSON_VALUE)
public class RentalController {

	private final RentalService rentalService;
	private final UserService userService;

	@GetMapping
	public RentalsResponse findAllRentals() {
		return this.rentalService.findAllRentals();
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MessageResponse> createRental(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("picture") MultipartFile picture,@ModelAttribute RentalRequest request) {
		UserResponse user = this.userService.getMe(authorizationHeader);
		return this.rentalService.createRental(user.getId(), picture, request);
	}

	@GetMapping("/{id}")
	public Optional<RentalEntity> findAllRentals(@PathVariable int id) {
		return this.rentalService.findById(id);
	}


	@PutMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MessageResponse> updateRental(@ModelAttribute RentalRequest request, @PathVariable int id) {
		return this.rentalService.updateRental(request, id);
	}
}
