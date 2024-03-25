package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.request.MessageRequest;
import com.openclassrooms.rentals.service.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Messages")
@AllArgsConstructor
@RequestMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Bearer Authentication")
public class MessageController {

	private final MessageService messageService;

	@PostMapping
	public ResponseEntity<?> createMessage(@RequestBody MessageRequest request) {
		return this.messageService.createMessage(request);
	}
}
