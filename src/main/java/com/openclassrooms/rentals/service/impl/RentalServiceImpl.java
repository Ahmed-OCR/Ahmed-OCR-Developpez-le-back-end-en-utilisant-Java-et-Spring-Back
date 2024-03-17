package com.openclassrooms.rentals.service.impl;

import com.openclassrooms.rentals.dto.request.RentalRequest;
import com.openclassrooms.rentals.dto.response.MessageResponse;
import com.openclassrooms.rentals.dto.response.RentalResponse;
import com.openclassrooms.rentals.dto.response.RentalsResponse;
import com.openclassrooms.rentals.entity.RentalEntity;
import com.openclassrooms.rentals.exception.RentalNotFoundException;
import com.openclassrooms.rentals.mapper.RentalMapper;
import com.openclassrooms.rentals.repository.RentalRepository;
import com.openclassrooms.rentals.service.RentalService;
import com.openclassrooms.rentals.util.MessageUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService {

	private final RentalRepository rentalRepository;

	@Override
	public RentalsResponse findAllRentals() {
		List<RentalEntity> rentalsEntity = this.rentalRepository.findAll();
		List<RentalResponse> rentalsResponse = RentalMapper.toRentalResponse(rentalsEntity);  //MAPPER ICI
		return new RentalsResponse(rentalsResponse);
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
	public ResponseEntity<MessageResponse> createRental(int id, MultipartFile picture, RentalRequest request) {
		RentalEntity rental = RentalMapper.mapToRental(id, picture, request);
		try {

			String imageUrl = saveImage(picture); // Enregistrez l'image et obtenez l'URL
			rental.setPicture(imageUrl);
			System.out.println("URL Image: " + imageUrl) ;
			this.rentalRepository.save(rental);
			return ResponseEntity.status(HttpStatus.CREATED).body(MessageUtil.returnMessage("Rental created !"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MessageUtil.returnMessage("An error occurred while creating the rental."));
		}
	}


	public String saveImage(MultipartFile file) {
//		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		String fileName = file.getOriginalFilename();
		String uploadDir = "./src/main/resources/static/images/";
		File directory = new File(uploadDir);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		File uploadedFile = new File(directory, fileName);
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), uploadedFile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		String imageUrl = "http://localhost:3001/images/" + fileName;
		return imageUrl;
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
			return ResponseEntity.status(HttpStatus.CREATED).body(MessageUtil.returnMessage("Rental updated !"));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MessageUtil.returnMessage("An error occurred while updating the rental."));
		}
	}
}

