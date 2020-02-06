package com.spiralforge.easefly.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightListResponseDto {

	private Integer flightId;
	private String flightName;
	private String sourceName;
	private String destinationName;
	private LocalDate flightDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private double price;
}
