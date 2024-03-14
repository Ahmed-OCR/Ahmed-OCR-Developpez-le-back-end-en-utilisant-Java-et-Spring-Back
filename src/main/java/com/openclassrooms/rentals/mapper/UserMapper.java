package com.openclassrooms.rentals.mapper;

import com.openclassrooms.rentals.dto.response.UserResponse;
import com.openclassrooms.rentals.entity.UserEntity;
import com.openclassrooms.rentals.util.DateUtil;

public class UserMapper {

	public static UserResponse UserEntitytoUserResponse(UserEntity userEntity) {
		UserResponse userResponse = new UserResponse();
		userResponse.setId(userEntity.getId());
		userResponse.setName(userEntity.getName());
		userResponse.setEmail(userEntity.getEmail());
		userResponse.setCreated_at(DateUtil.parseTimestamp(userEntity.getCreatedAt()));
		userResponse.setUpdated_at(DateUtil.parseTimestamp(userEntity.getUpdatedAt()));
		return userResponse;
	}


}
