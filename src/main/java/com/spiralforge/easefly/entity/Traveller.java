package com.spiralforge.easefly.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "traveller")
public class Traveller implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer travellerId;
	private String travellerName;
	private String gender;
	private String emailId;
	private Integer age;
	private Boolean primaryTravellerFlag;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "booking_id")
	private Booking booking;
}
