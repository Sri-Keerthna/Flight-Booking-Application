package com.spiralforge.easefly.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "booking")
@SequenceGenerator(name = "creditcardsequence", initialValue = 100100)
public class Booking implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "creditcardsequence")
	private Integer bookingId;
	private String primaryEmailid;
	private Integer noOfSeats;
	private Double totalPrice;
	private LocalDate travelDate;
	private String status;
	private String paymentType;

	private LocalDateTime bookingCreatedDate;
	private LocalDateTime bookingUpdatedDate;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "flight_id")
	private Flight flight;

}
