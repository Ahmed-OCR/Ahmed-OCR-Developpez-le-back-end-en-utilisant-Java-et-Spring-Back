package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.dto.request.MessageRequest;
import com.openclassrooms.rentals.dto.response.MessageResponse;

public interface MessageService {

	MessageResponse createMessage(MessageRequest request);
}
