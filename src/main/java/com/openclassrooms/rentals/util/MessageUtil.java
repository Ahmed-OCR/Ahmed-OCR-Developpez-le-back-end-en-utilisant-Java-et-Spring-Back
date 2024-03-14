package com.openclassrooms.rentals.util;

import com.openclassrooms.rentals.dto.response.MessageResponse;

public class MessageUtil {

	public static MessageResponse returnMessage(String message) {
		return MessageResponse.builder()
				.message(message)
				.build();
	}
}
