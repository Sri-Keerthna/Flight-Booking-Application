package com.spiralforge.easefly.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightRequestDto implements Serializable{

	private String sourceName;
	private String destinationName;
	private LocalDate date;
	private Integer noOfTraveller;
}
