package com.openclassrooms.rentals.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

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

	@Column(name = "owner_id")
	private int owner_id;

//	@ManyToOne
//	@JsonIgnore
//	@NotNull
//	@JoinColumn(name = "owner_id", insertable = false, updatable = false)
//	private UserEntity owner;

	@Column(name = "created_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "Europe/Paris")
	private Timestamp created_at;

	@Column(name = "updated_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "Europe/Paris")
	private Timestamp updated_at;

	@PrePersist
	protected void onCreate() {
		created_at = new Timestamp(System.currentTimeMillis());
	}

	@PreUpdate
	protected void onUpdate() {
		updated_at = new Timestamp(System.currentTimeMillis());
	}
}
