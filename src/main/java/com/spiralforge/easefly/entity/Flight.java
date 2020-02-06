package com.spiralforge.easefly.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "flight")
public class Flight implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer flightId;
	private String flightNumber;
	private String flightName;
	private String sourceName;
	private String destinationName;
	private LocalDate flightDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private Double price;
	private Integer totalSeat;
}
