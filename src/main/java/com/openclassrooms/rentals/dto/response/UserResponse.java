package com.openclassrooms.rentals.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class UserResponse {
	int id;
	String name;
	String email;
	String created_at;
	String updated_at;

}
