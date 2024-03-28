package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.dto.request.MessageRequest;
import org.springframework.http.ResponseEntity;

public interface MessageService {

	ResponseEntity<?> createMessage(MessageRequest request);
}
