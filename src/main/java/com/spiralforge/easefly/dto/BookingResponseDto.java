package com.spiralforge.easefly.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponseDto implements Serializable{
	
	private Integer bookingId;
	private Integer statusCode;
	private String message;
}
