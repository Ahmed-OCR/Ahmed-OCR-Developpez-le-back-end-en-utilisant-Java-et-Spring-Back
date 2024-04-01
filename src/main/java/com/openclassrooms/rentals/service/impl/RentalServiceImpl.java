package com.openclassrooms.rentals.service.impl;

import com.openclassrooms.rentals.dto.request.RentalRequest;
import com.openclassrooms.rentals.dto.response.MessageResponse;
import com.openclassrooms.rentals.dto.response.RentalResponse;
import com.openclassrooms.rentals.dto.response.RentalsResponse;
import com.openclassrooms.rentals.entity.RentalEntity;
import com.openclassrooms.rentals.exception.rentals.RentalCreationException;
import com.openclassrooms.rentals.exception.rentals.RentalNotFoundException;
import com.openclassrooms.rentals.exception.rentals.RentalUpdateException;
import com.openclassrooms.rentals.mapper.RentalMapper;
import com.openclassrooms.rentals.repository.RentalRepository;
import com.openclassrooms.rentals.service.RentalService;
import com.openclassrooms.rentals.util.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
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
	public RentalsResponse findAllRentals() throws RentalNotFoundException{
		try {
			List<RentalEntity> rentalsEntity = this.rentalRepository.findAll();
			List<RentalResponse> rentalsResponse = RentalMapper.toRentalResponse(rentalsEntity);
			return new RentalsResponse(rentalsResponse);
		}
		catch (RentalNotFoundException e) {
			throw new RentalNotFoundException();
		}
	}

	@Override
	public RentalResponse findRentalById(int id) throws RentalNotFoundException {
		try {
			Optional<RentalEntity> rental = this.rentalRepository.findById(id);
			if (rental.isEmpty()) {
				throw new RentalNotFoundException();
			}
			return RentalMapper.toRentalResponseSetter(rental.get()); // .get() Récupére l'entité à l'intérieur de l'Optional
		}
		catch (RentalNotFoundException e) {
			throw new RentalNotFoundException();
		}
	}

	@Override
	public MessageResponse createRental(int id, MultipartFile picture, RentalRequest request, HttpServletRequest httpServletRequest) throws RentalCreationException {
		if (!this.validateRentaRequest(request, true)) {
			throw new RentalCreationException();
		}

		RentalEntity rental = RentalMapper.mapToRental(id, request);
		try {
			String imageUrl = saveImage(picture, httpServletRequest);
			rental.setPicture(imageUrl);
			this.rentalRepository.save(rental);
			return MessageUtil.returnMessage("Rental created !");
		} catch (Exception e) {
			throw new RentalCreationException();
		}
	}

	@Override
	public MessageResponse updateRental(RentalRequest request, int id) throws RentalUpdateException {
		if (!this.validateRentaRequest(request, false)) {
			throw new RentalUpdateException();
		}
		Optional<RentalEntity> optionalRental = rentalRepository.findById(id);
		if (optionalRental.isPresent()) {
			RentalEntity existingRental = optionalRental.get();
			// Copie toutes les propriétés de la location vers la location existante
			BeanUtils.copyProperties(request, existingRental, "id");
			this.rentalRepository.save(existingRental);
			return MessageUtil.returnMessage("Rental updated !");
		} else {
			throw new RentalUpdateException();
		}
	}

	public boolean validateRentaRequest(RentalRequest rentalRequest, boolean validatePicture) {
		if (rentalRequest == null
				|| rentalRequest.getName() == null || rentalRequest.getName().isEmpty()
				|| rentalRequest.getSurface() <= 0
				|| rentalRequest.getPrice() <= 0
				|| (validatePicture && (rentalRequest.getPicture() == null || rentalRequest.getPicture().isEmpty()))
				|| rentalRequest.getDescription() == null || rentalRequest.getDescription().isEmpty()) {
			return false;
		}
		return true;
	}

	public String saveImage(MultipartFile file, HttpServletRequest httpServletRequest) {
//		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		String fileName = file.getOriginalFilename();
		String uploadDir = "/images/";

		File directory = new File("." + uploadDir); // On part du repertoire courant, d'ou le "." dans le chemin
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

		// Construction du chemin de l'image
		return  httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" +
				httpServletRequest.getServerPort() + httpServletRequest.getContextPath() +
				httpServletRequest.getServletPath() + uploadDir + fileName;
	}
}

