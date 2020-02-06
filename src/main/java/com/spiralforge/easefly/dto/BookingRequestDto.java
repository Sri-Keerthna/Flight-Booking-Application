package com.spiralforge.easefly.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.spiralforge.easefly.entity.Traveller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequestDto implements Serializable{

	private LocalDate travelDate;
	private String paymentType;
	private List<Traveller> travellerList;
	
}
