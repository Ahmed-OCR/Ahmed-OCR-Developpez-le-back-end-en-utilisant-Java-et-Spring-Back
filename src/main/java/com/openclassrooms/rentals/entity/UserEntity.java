package com.openclassrooms.rentals.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name="USERS")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	private String email;

	private String name;
	private String password;

//	@OneToMany(mappedBy = "user")
//	private Set<MessageEntity> messages;
//
//	@OneToMany(mappedBy = "owner")
//	private Set<RentalEntity> rentals;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	// Getters and setters
	@PrePersist
	protected void onCreate() {
		createdAt = new Timestamp(System.currentTimeMillis());
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = new Timestamp(System.currentTimeMillis());
	}
}


