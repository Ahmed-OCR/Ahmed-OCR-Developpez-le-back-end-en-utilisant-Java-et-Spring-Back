package com.openclassrooms.rentals.service.impl;

import com.openclassrooms.rentals.dto.request.MessageRequest;
import com.openclassrooms.rentals.dto.response.MessageResponse;
import com.openclassrooms.rentals.entity.MessageEntity;
import com.openclassrooms.rentals.exception.CustomAuthenticationException;
import com.openclassrooms.rentals.mapper.MessageMapper;
import com.openclassrooms.rentals.repository.MessageRepository;
import com.openclassrooms.rentals.service.MessageService;
import com.openclassrooms.rentals.util.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageRepository messageRepository;

	@Override
	public MessageResponse createMessage(MessageRequest request) throws IllegalArgumentException, CustomAuthenticationException {
		if (!isValidateMessage(request)) {
			throw new IllegalArgumentException();
		}

		try {
			MessageEntity rental = MessageMapper.mapToMessageEntity(request);
			this.messageRepository.save(rental);
			return MessageUtil.returnMessage("Message send with success");
		} catch (IllegalArgumentException e) { //HTTP 400
			throw new IllegalArgumentException();
		} catch (CustomAuthenticationException e) { //HTTP 401
			throw new CustomAuthenticationException("");
		}
	}
	private boolean isValidateMessage(MessageRequest request) {
		if (request.getUser_id() <= 0 ||
				request.getRental_id() <= 0 ||
				request.getMessage() == null || request.getMessage().isEmpty()) {
			return false;
		}
		return true;
	}
}
