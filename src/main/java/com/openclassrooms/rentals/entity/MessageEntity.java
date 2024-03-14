package com.openclassrooms.rentals.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "MESSAGES")
public class MessageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "rental_id")
	private RentalEntity rental;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Column(length = 2000)
	private String message;

	@Column(name = "created_at")
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
