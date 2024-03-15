package com.openclassrooms.rentals.service.impl;

import com.openclassrooms.rentals.dto.request.MessageRequest;
import com.openclassrooms.rentals.entity.MessageEntity;
import com.openclassrooms.rentals.mapper.MessageMapper;
import com.openclassrooms.rentals.repository.MessageRepository;
import com.openclassrooms.rentals.service.MessageService;
import com.openclassrooms.rentals.util.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageRepository messageRepository;

	@Override
	public ResponseEntity<?> createMessage(MessageRequest request) {
		MessageEntity rental = MessageMapper.mapToMessageEntity(request);
		try {
			this.messageRepository.save(rental);
			return ResponseEntity.status(HttpStatus.CREATED).body(MessageUtil.returnMessage("Message send with success"));
		} catch (IllegalArgumentException e) { //HTTP 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.EMPTY_MAP);
		} catch (AuthenticationException e) { //HTTP 401
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
		}
	}
}
