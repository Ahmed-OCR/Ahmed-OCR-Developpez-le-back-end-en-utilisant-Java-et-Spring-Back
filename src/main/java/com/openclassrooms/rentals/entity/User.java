package com.openclassrooms.rentals.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name="user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	private String email;

	private String name;

	private String password;

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


