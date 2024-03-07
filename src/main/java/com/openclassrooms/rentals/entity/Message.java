package com.openclassrooms.rentals.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name="message")
@Data
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int rental_id;
	private int user_id;
	@Column(length = 2000)
	private String message;
	private Timestamp created_at;
	private Timestamp updated_at;
}
