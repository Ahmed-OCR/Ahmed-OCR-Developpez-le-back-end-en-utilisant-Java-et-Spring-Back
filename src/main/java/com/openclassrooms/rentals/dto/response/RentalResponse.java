package com.openclassrooms.rentals.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalResponse {
	int id;
	String name;
	int surface;
	int price;
	String picture;
	String description;
	int owner_id;
	Date created_at;
	Date updated_at;
}
