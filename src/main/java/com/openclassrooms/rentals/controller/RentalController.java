package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.response.RentalResponse;
import com.openclassrooms.rentals.entity.Rental;
import com.openclassrooms.rentals.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/rentals", produces = MediaType.APPLICATION_JSON_VALUE)
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
	public Optional<Rental> findAllRentals(@PathVariable int id) {
//		System.out.println("L'ID de l'utilisateur est : " + id);
		return this.rentalService.findById(id);
	}
}
