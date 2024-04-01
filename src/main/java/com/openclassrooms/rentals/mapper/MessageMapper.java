package com.openclassrooms.rentals.mapper;

import com.openclassrooms.rentals.dto.request.MessageRequest;
import com.openclassrooms.rentals.entity.MessageEntity;
import org.springframework.stereotype.Component;

@Component

public class MessageMapper {

	public static MessageEntity mapToMessageEntity(MessageRequest messageRequest) {
		MessageEntity message = new MessageEntity();
		message.setMessage(messageRequest.getMessage());
		message.setRental_id(messageRequest.getRental_id());
		message.setUser_id(messageRequest.getUser_id());
		return message;
	}
}