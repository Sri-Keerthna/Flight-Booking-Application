package com.spiralforge.easefly.dto;

import java.io.Serializable;
import java.util.List;

import com.spiralforge.easefly.entity.Flight;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightResponseDto implements Serializable{
	
	private List<Flight> flightList;
	private Integer statusCode;
	private String message;
}
