package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.dto.response.RentalResponse;
import com.openclassrooms.rentals.entity.Rental;
import com.openclassrooms.rentals.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
	private final RentalRepository rentalRepository;

	@Autowired
	public RentalService(RentalRepository rentalRepository) {
		this.rentalRepository = rentalRepository;
	}

	public RentalResponse findAllRentals() {
		List<Rental> rentals = this.rentalRepository.findAll();
		return new RentalResponse(rentals);
	}

	public Optional<Rental> findById(int id) {
		Optional<Rental> rental = this.rentalRepository.findById(id);
		return rental;
//		System.out.println(rental.get().);
	}
}
