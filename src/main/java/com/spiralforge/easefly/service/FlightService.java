package com.spiralforge.easefly.service;

import java.time.LocalDate;
import java.util.List;

import com.spiralforge.easefly.dto.FlightListResponseDto;
import com.spiralforge.easefly.exception.FlightNotFoundException;

/**
 * @author Sri Keerthna.
 * @since 2020-02-03.
 *
 */
public interface FlightService {

	public List<FlightListResponseDto> searchFlight(String sourceName, String destinationName, LocalDate date,
			Integer noOfTraveller) throws FlightNotFoundException;

}
