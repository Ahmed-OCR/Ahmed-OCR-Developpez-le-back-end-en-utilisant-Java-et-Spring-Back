package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.request.MessageRequest;
import com.openclassrooms.rentals.dto.response.MessageResponse;
import com.openclassrooms.rentals.exception.CustomAuthenticationException;
import com.openclassrooms.rentals.service.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Tag(name = "Messages")
@AllArgsConstructor
@RequestMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Bearer Authentication")
public class MessageController {

	private final MessageService messageService;

	@PostMapping
	public ResponseEntity<?> createMessage(@RequestBody MessageRequest request) {
		try {
			MessageResponse message = this.messageService.createMessage(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(message);
		}
		catch (IllegalArgumentException e) { //HTTP 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.EMPTY_MAP);
		} catch (CustomAuthenticationException e) { //HTTP 401
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
		}
	}
}
