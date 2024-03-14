package com.openclassrooms.rentals.service.impl;

import com.openclassrooms.rentals.dto.request.RentalRequest;
import com.openclassrooms.rentals.dto.response.RentalResponse;
import com.openclassrooms.rentals.entity.RentalEntity;
import com.openclassrooms.rentals.exception.RentalNotFoundException;
import com.openclassrooms.rentals.mapper.RentalMapper;
import com.openclassrooms.rentals.repository.RentalRepository;
import com.openclassrooms.rentals.service.RentalService;
import lombok.AllArgsConstructor;
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
	public void createRental(RentalRequest request) {
		RentalEntity rental = RentalMapper.mapToRental(request);
		this.rentalRepository.save(rental);
	}

}
