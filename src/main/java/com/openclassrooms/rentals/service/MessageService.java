package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.dto.request.MessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

	ResponseEntity<?> createMessage(MessageRequest request);
}
