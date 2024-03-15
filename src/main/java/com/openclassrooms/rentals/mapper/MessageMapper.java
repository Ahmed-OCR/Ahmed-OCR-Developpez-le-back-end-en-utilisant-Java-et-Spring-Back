package com.openclassrooms.rentals.mapper;

import com.openclassrooms.rentals.dto.request.MessageRequest;
import com.openclassrooms.rentals.entity.MessageEntity;

public class MessageMapper {

	public static MessageEntity mapToMessageEntity(MessageRequest messageRequest) {
		MessageEntity message = new MessageEntity();
		message.setMessage(messageRequest.getMessage());
		message.setUser_id(messageRequest.getUser_id());
		message.setRental_id(messageRequest.getRental_id());
		return message;
	}
}
