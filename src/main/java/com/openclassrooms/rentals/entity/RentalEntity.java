package com.openclassrooms.rentals.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@Table(name="RENTALS")
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

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private UserEntity owner;

	@OneToMany(mappedBy = "rental")
	private Set<MessageEntity> messages;

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
