package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.dto.request.RentalRequest;
import com.openclassrooms.rentals.dto.response.MessageResponse;
import com.openclassrooms.rentals.dto.response.RentalResponse;
import com.openclassrooms.rentals.dto.response.RentalsResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface RentalService {

	RentalsResponse findAllRentals();

	RentalResponse findRentalById(int id);

	MessageResponse createRental(int id,MultipartFile picture,RentalRequest request, HttpServletRequest httpServletRequest) ;

	MessageResponse updateRental(RentalRequest request, int id);
}
