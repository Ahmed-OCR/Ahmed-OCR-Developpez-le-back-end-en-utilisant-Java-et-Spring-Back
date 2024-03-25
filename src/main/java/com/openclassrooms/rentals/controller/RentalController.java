package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.request.RentalRequest;
import com.openclassrooms.rentals.dto.response.MessageResponse;
import com.openclassrooms.rentals.dto.response.RentalsResponse;
import com.openclassrooms.rentals.dto.response.UserResponse;
import com.openclassrooms.rentals.entity.RentalEntity;
import com.openclassrooms.rentals.service.RentalService;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "Locations")
@RequestMapping(value = "/rentals", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Bearer Authentication")
public class RentalController {

	private final RentalService rentalService;
	private final UserService userService;

	@GetMapping
	public RentalsResponse findAllRentals() {
		return this.rentalService.findAllRentals();
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MessageResponse> createRental(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("picture") MultipartFile picture,@ModelAttribute RentalRequest request, HttpServletRequest httpServletRequest) {
		UserResponse user = this.userService.getMe(authorizationHeader);
		return this.rentalService.createRental(user.getId(), picture, request, httpServletRequest);
	}

	@GetMapping("/{id}")
	public Optional<RentalEntity> findAllRentals(@PathVariable int id) {
		return this.rentalService.findById(id);
	}

	@PutMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MessageResponse> updateRental(@ModelAttribute RentalRequest request, @PathVariable int id) {
		return this.rentalService.updateRental(request, id);
	}

	@GetMapping("/images/{image}")
	@Hidden
	public ResponseEntity<Resource> getImage(@PathVariable String image) {
		Path imagePath = Paths.get("/images/").resolve(image);
		Resource resource;
		try {
			resource = new UrlResource(imagePath.toUri());

			// Vérifie si la ressource existe et est lisible
			if (!resource.exists() || !resource.isReadable()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			// Set le content type par exemple avec image/jpeg
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);

			return ResponseEntity.ok()
					.headers(headers)
					.body(resource);

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
