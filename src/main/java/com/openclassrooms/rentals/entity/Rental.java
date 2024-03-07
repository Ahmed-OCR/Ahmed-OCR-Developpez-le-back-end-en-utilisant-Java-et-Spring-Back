package com.openclassrooms.rentals.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name="rental")
@Data
public class Rental {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int surface;
	private int price;
	private String picture;
	@Column(length = 2000)
	private String description;
	private int owner_id; // owner_id NOT NULL
	private Timestamp created_at;
	private Timestamp updated_at;

}
