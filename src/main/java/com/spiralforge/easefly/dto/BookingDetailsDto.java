package com.spiralforge.easefly.dto;

import java.io.Serializable;
import java.util.List;

import com.spiralforge.easefly.entity.Booking;
import com.spiralforge.easefly.entity.Traveller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDetailsDto implements Serializable{
	
	private Booking booking;
	private List<Traveller> travellerList;
	private Integer statusCode;
	private String message;
}
