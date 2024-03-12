package com.openclassrooms.rentals.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name="rental")
@Data
public class RentalEntity {

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

	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@PrePersist
	protected void onCreate() {
		createdAt = new Timestamp(System.currentTimeMillis());
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = new Timestamp(System.currentTimeMillis());
	}

}
