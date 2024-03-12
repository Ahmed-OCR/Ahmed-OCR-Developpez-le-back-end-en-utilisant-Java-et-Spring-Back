package com.openclassrooms.rentals.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalRequest {
		String name;
		int surface;
		int price;
		String picture;
		String description;
}
